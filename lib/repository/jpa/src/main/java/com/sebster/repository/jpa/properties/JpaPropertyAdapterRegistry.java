package com.sebster.repository.jpa.properties;

import static java.util.List.copyOf;

import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.Property;
import lombok.NonNull;

@Component
public class JpaPropertyAdapterRegistry {

	private final @NonNull List<JpaPropertyAdapter<?>> adapters;

	public JpaPropertyAdapterRegistry(@NonNull List<JpaPropertyAdapter<?>> adapters) {
		this.adapters = copyOf(adapters);
	}

	public <T, V> SingularAttribute<T, V> toJpaAttribute(@NonNull Property<T, V> property) {
		for (JpaPropertyAdapter<?> adapter : adapters) {
			if (adapter.canAdapt(property)) {
				@SuppressWarnings("unchecked")
				JpaPropertyAdapter<T> tAdapter = (JpaPropertyAdapter<T>) adapter;
				return tAdapter.adapt(property);
			}
		}
		throw new UnsupportedOperationException("Unmapped property: " + property);
	}

	public <T, V> Path<V> toJpaPath(@NonNull Property<? super T, V> property, @NonNull Root<T> root) {
		return root.get(toJpaAttribute(property));
	}

}
