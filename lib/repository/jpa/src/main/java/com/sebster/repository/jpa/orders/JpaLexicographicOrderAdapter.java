package com.sebster.repository.jpa.orders;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.orders.LexicographicOrder;
import com.sebster.repository.api.orders.Order;

@Component
public class JpaLexicographicOrderAdapter<T> extends AbstractJpaOrderAdapter<T> {

	@Override
	public boolean canAdapt(Order<?> order) {
		return order instanceof LexicographicOrder;
	}

	@Override
	public List<javax.persistence.criteria.Order> adapt(Order<T> order, Root<? extends T> root, CriteriaBuilder cb) {
		LexicographicOrder<T> lexicographicOrder = cast(order);
		return lexicographicOrder.getOrders().stream()
				.flatMap(subOrder -> toJpaOrderList(subOrder, root, cb).stream())
				.collect(toList());
	}

}
