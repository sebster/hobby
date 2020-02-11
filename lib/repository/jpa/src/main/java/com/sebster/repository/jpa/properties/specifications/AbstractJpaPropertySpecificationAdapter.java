package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.Property;
import com.sebster.repository.jpa.properties.JpaPropertyAdapterRegistry;
import com.sebster.repository.jpa.specifications.AbstractJpaSpecificationAdapter;
import lombok.NonNull;

@Component
public abstract class AbstractJpaPropertySpecificationAdapter<T> extends AbstractJpaSpecificationAdapter<T> {

	private @NonNull JpaPropertyAdapterRegistry propertyAdapterRegistry;

	protected final <S, V> SingularAttribute<S, V> toJpaAttribute(@NonNull Property<S, V> property) {
		return propertyAdapterRegistry.toJpaAttribute(property);
	}

	protected final <S, V> Path<V> toJpaPath(@NonNull Property<S, V> property, @NonNull Root<? extends S> root) {
		return propertyAdapterRegistry.toJpaPath(property, root);
	}

	@Autowired
	void setPropertyAdapterRegistry(@NonNull JpaPropertyAdapterRegistry propertyAdapterRegistry) {
		this.propertyAdapterRegistry = propertyAdapterRegistry;
	}

}
