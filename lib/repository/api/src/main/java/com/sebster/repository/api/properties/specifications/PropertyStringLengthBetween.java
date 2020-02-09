package com.sebster.repository.api.properties.specifications;

import static lombok.AccessLevel.PACKAGE;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class PropertyStringLengthBetween<T> implements PropertySpecification<T, String> {

	@NonNull Property<T, String> property;
	int lowInclusive;
	int highInclusive;

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		String propertyValue = property.getValue(object);
		return propertyValue != null && lowInclusive <= propertyValue.length() && propertyValue.length() <= highInclusive;
	}

}
