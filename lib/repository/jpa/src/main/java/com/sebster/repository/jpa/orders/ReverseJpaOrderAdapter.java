package com.sebster.repository.jpa.orders;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.orders.Order;
import com.sebster.repository.api.orders.ReverseOrder;

@Component
public class ReverseJpaOrderAdapter<T> extends AbstractJpaOrderAdapter<T> {

	@Override
	public boolean canAdapt(Order<?> order) {
		return order instanceof ReverseOrder;
	}

	@Override
	public List<javax.persistence.criteria.Order> adapt(Order<T> order, Root<? extends T> root, CriteriaBuilder cb) {
		ReverseOrder<T> reverseOrder = cast(order);
		var jpaOrders = toJpaOrderList(reverseOrder.getOrder(), root, cb);
		return jpaOrders.stream().map(jpaOrder -> jpaOrder.reverse()).collect(toList());
	}

}
