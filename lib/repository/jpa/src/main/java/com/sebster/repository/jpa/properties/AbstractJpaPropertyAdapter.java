package com.sebster.repository.jpa.properties;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import com.sebster.repository.api.properties.Property;
import lombok.NonNull;

public abstract class AbstractJpaPropertyAdapter<T> implements JpaPropertyAdapter<T> {

	private final Map<Property<?, ?>, SingularAttribute<?, ?>> adapterMap = new HashMap<>();

	protected final <V> void map(@NonNull Property<T, V> property, @NonNull SingularAttribute<T, V> attribute) {
		adapterMap.put(property, attribute);
	}

	@Override
	public final boolean canAdapt(@NonNull Property<?, ?> property) {
		return adapterMap.containsKey(property);
	}

	@Override
	public final <V> SingularAttribute<T, V> adapt(@NonNull Property<T, V> property) {
		@SuppressWarnings("unchecked")
		SingularAttribute<T, V> attribute = (SingularAttribute<T, V>) adapterMap.get(property);
		if (attribute == null) {
			throw new IllegalArgumentException("Unmapped property: " + property);
		}
		return attribute;
	}

}
