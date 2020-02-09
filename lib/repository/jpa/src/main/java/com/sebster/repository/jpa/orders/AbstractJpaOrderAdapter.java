package com.sebster.repository.jpa.orders;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.orders.Order;
import lombok.NonNull;

@Component
public abstract class AbstractJpaOrderAdapter<T> implements JpaOrderAdapter<T> {

	private @NonNull JpaOrderAdapterRegistry orderAdapterRegistry;

	protected final <S> List<javax.persistence.criteria.Order> toJpaOrderList(
			@NonNull Order<S> order,
			@NonNull Root<? extends S> root,
			@NonNull CriteriaBuilder cb
	) {
		return orderAdapterRegistry.toJpaOrderList(order, root, cb);
	}

	protected final <U, S extends Order<U>> S cast(@NonNull Order<U> order) {
		@SuppressWarnings("unchecked")
		S castOrder = (S) order;
		return castOrder;
	}

	@Autowired
	void setOrderAdapterRegistry(@NonNull JpaOrderAdapterRegistry orderAdapterRegistry) {
		this.orderAdapterRegistry = orderAdapterRegistry;
	}

}
