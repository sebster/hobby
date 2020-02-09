package com.sebster.repository.jpa.properties;

import javax.persistence.metamodel.SingularAttribute;

import com.sebster.repository.api.properties.Property;

public interface JpaPropertyAdapter<T> {

	boolean canAdapt(Property<?, ?> property);

	<V> SingularAttribute<T, V> adapt(Property<T, V> property);

}
