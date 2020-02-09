package com.sebster.repository.jpa;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.util.ProxyUtils;
import org.springframework.transaction.annotation.Transactional;

import com.sebster.repository.api.Page;
import com.sebster.repository.api.PageRequest;
import com.sebster.repository.api.Repository;
import com.sebster.repository.api.orders.Order;
import com.sebster.repository.api.specifications.Specification;
import com.sebster.repository.jpa.orders.JpaOrderAdapterRegistry;
import com.sebster.repository.jpa.specifications.JpaSpecificationAdapterRegistry;
import lombok.NonNull;

@Transactional(propagation = MANDATORY)
public abstract class JpaRepository<T> implements Repository<T> {

	private final @NonNull Class<T> domainClass;
	private @NonNull EntityManager entityManager;
	private @NonNull JpaEntityInformation<T, ?> entityInformation;
	private @NonNull JpaSpecificationAdapterRegistry specificationAdapterRegistry;
	private @NonNull JpaOrderAdapterRegistry orderAdapterRegistry;

	public JpaRepository(@NonNull Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	@PostConstruct
	protected void init() {
		entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
	}

	@Override
	public Stream<T> findAll(@NonNull Specification<? super T> specification) {
		return entityManager.createQuery(toQuery(specification, null)).getResultStream();
	}

	@Override
	public Stream<T> findAll(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return entityManager.createQuery(toQuery(specification, order)).getResultStream();
	}

	@Override
	public Page<T> findAll(@NonNull Specification<? super T> specification, @NonNull PageRequest pageRequest) {
		CriteriaQuery<T> query = toQuery(specification, null);
		return selectPage(query, () -> count(specification), pageRequest);
	}

	@Override
	public Page<T> findAll(
			@NonNull Specification<? super T> specification,
			@NonNull Order<? super T> order,
			@NonNull PageRequest pageRequest
	) {
		CriteriaQuery<T> query = toQuery(specification, order);
		return selectPage(query, () -> count(specification), pageRequest);
	}

	@Override
	public long count(@NonNull Specification<? super T> specification) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		Class<T> domainClass = entityInformation.getJavaType();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<T> root = query.from(domainClass);
		query.select(cb.count(cb.literal(1)));
		query.where(specificationAdapterRegistry.toJpaPredicate(specification, root, query, cb));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public void add(@NonNull T object) {
		if (!entityInformation.isNew(object)) {
			throw new IllegalStateException("Object already present in repository: " + object);
		}
		entityManager.persist(object);
	}

	@Override
	public void remove(@NonNull T object) {
		if (entityInformation.isNew(object)) {
			throw new IllegalStateException("Object not present in repository: " + object);
		}
		Class<?> type = ProxyUtils.getUserClass(object);
		Object existing = entityManager.find(type, entityInformation.getId(object));
		if (existing == null) {
			throw new IllegalStateException("Object not present in repository: " + object);
		}
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
	}

	@Override
	public void removeAll(@NonNull Specification<? super T> specification) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		Class<T> domainClass = entityInformation.getJavaType();
		CriteriaDelete<T> delete = cb.createCriteriaDelete(domainClass);
		Root<T> root = delete.from(domainClass);
		delete.where(specificationAdapterRegistry.toJpaPredicate(specification, root, delete, cb));
		entityManager.createQuery(delete).executeUpdate();
	}

	private CriteriaQuery<T> toQuery(Specification<? super T> specification, Order<? super T> order) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		Class<T> domainClass = entityInformation.getJavaType();
		CriteriaQuery<T> query = cb.createQuery(domainClass);
		Root<T> root = query.from(domainClass);
		query.select(root);
		query.where(specificationAdapterRegistry.toJpaPredicate(specification, root, query, cb));
		if (order != null) {
			query.orderBy(orderAdapterRegistry.toJpaOrderList(order, root, cb));
		}
		return query;
	}

	private Page<T> selectPage(CriteriaQuery<T> query, @NonNull Supplier<Long> countQuery, @NonNull PageRequest pageRequest) {
		List<T> pageItems = entityManager
				.createQuery(query)
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();
		return pageRequest.isIncludeTotalNumberOfItems() ?
				Page.of(countQuery.get(), pageRequest.getPageNumber(), pageRequest.getPageSize(), pageItems) :
				Page.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageItems);
	}

	@Autowired
	void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	void setSpecificationAdapterRegistry(JpaSpecificationAdapterRegistry specificationAdapterRegistry) {
		this.specificationAdapterRegistry = specificationAdapterRegistry;
	}

	@Autowired
	void setOrderAdapterRegistry(JpaOrderAdapterRegistry orderAdapterRegistry) {
		this.orderAdapterRegistry = orderAdapterRegistry;
	}

}
