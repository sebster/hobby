package com.sebster.repository.api.properties.specifications;

import static lombok.AccessLevel.PACKAGE;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class PropertyGreaterThanValue<T, V extends Number & Comparable<? super V>> implements PropertySpecification<T, V> {

	@NonNull Property<T, V> property;
	@NonNull V value;

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		V propertyValue = property.getValue(object);
		return propertyValue.compareTo(value) > 0;
	}

}
