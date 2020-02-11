package com.sebster.repository.api.properties.specifications;

import static lombok.AccessLevel.PACKAGE;

import java.util.Objects;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class PropertyEqualToValue<T, V> implements PropertySpecification<T, V> {

	@NonNull Property<T, V> property;
	V value;

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		V propertyValue = property.getValue(object);
		return Objects.equals(propertyValue, value);
	}

}
