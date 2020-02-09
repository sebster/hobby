package com.sebster.repository.api.properties.specifications;

import static org.apache.commons.lang3.Validate.noNullElements;

import java.util.Collection;
import java.util.List;

import com.sebster.repository.api.properties.Property;
import lombok.NonNull;
import lombok.Value;

@Value
public final class PropertyInValues<T, V> implements PropertySpecification<T, V> {

	@NonNull Property<T, V> property;
	@NonNull List<? extends V> values;

	PropertyInValues(@NonNull Property<T, V> property, @NonNull Collection<? extends V> values) {
		this.property = property;
		this.values = List.copyOf(noNullElements(values));
	}

	@Override
	public boolean isSatisfiedBy(@NonNull T object) {
		V propertyValue = property.getValue(object);
		return values.contains(propertyValue);
	}

}
