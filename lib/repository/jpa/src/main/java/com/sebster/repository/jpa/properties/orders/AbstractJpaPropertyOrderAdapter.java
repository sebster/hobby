package com.sebster.repository.jpa.properties.orders;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.Property;
import com.sebster.repository.jpa.orders.AbstractJpaOrderAdapter;
import com.sebster.repository.jpa.properties.JpaPropertyAdapterRegistry;
import lombok.NonNull;

@Component
public abstract class AbstractJpaPropertyOrderAdapter<T> extends AbstractJpaOrderAdapter<T> {

	private @NonNull JpaPropertyAdapterRegistry propertyAdapterRegistry;

	protected final <S, W> SingularAttribute<S, W> toJpaAttribute(@NonNull Property<S, W> property) {
		return propertyAdapterRegistry.toJpaAttribute(property);
	}

	protected final <S, V> Path<V> toJpaPath(@NonNull Property<? super S, V> property, @NonNull Root<S> root) {
		return propertyAdapterRegistry.toJpaPath(property, root);
	}

	@Autowired
	void setPropertyAdapterRegistry(@NonNull JpaPropertyAdapterRegistry propertyAdapterRegistry) {
		this.propertyAdapterRegistry = propertyAdapterRegistry;
	}

}
