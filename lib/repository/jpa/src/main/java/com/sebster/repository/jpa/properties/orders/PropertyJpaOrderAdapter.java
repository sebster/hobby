package com.sebster.repository.jpa.properties.orders;

import static java.util.Collections.singletonList;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.orders.Order;
import com.sebster.repository.api.properties.orders.PropertyOrder;

@Component
public class PropertyJpaOrderAdapter<T> extends AbstractJpaPropertyOrderAdapter<T> {

	@Override
	public boolean canAdapt(Order<?> order) {
		return order instanceof PropertyOrder;
	}

	@Override
	public List<javax.persistence.criteria.Order> adapt(Order<T> order, Root<? extends T> root, CriteriaBuilder cb) {
		PropertyOrder<T, ?> propertyOrder = cast(order);
		return singletonList(cb.asc(toJpaPath(propertyOrder.getProperty(), root)));
	}

}
