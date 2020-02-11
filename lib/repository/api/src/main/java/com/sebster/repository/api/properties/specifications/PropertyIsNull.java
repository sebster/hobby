package com.sebster.repository.api.properties.specifications;

import static lombok.AccessLevel.PACKAGE;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class PropertyIsNull<T, V> implements PropertySpecification<T, V> {

	@NonNull Property<T, V> property;

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		return property.getValue(object) == null;
	}

}
