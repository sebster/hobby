package com.sebster.repository.api.properties.specifications;

import com.sebster.repository.api.properties.Property;
import lombok.NonNull;
import lombok.Value;

@Value
public class PropertyLessThanOrEqualToValue<T, V extends Number & Comparable<? super V>> implements PropertySpecification<T, V> {

	@NonNull Property<T, V> property;
	@NonNull V value;

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		V propertyValue = property.getValue(object);
		return propertyValue.compareTo(value) <= 0;
	}

}
