package com.sebster.repository.jpa.orders;

import static java.util.List.copyOf;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.orders.Order;
import lombok.NonNull;

@Component
public class JpaOrderAdapterRegistry {

	private @NonNull List<JpaOrderAdapter<?>> adapters;

	public <T> List<javax.persistence.criteria.Order> toJpaOrderList(
			@NonNull Order<T> order,
			@NonNull Root<? extends T> root,
			@NonNull CriteriaBuilder cb
	) {
		for (JpaOrderAdapter<?> adapter : adapters) {
			if (adapter.canAdapt(order)) {
				@SuppressWarnings("unchecked")
				JpaOrderAdapter<T> tAdapter = (JpaOrderAdapter<T>) adapter;
				return tAdapter.adapt(order, root, cb);
			}
		}
		throw new UnsupportedOperationException("Unmapped order: " + order);
	}

	@Autowired
	void setAdapters(@NonNull List<JpaOrderAdapter<?>> adapters) {
		this.adapters = copyOf(adapters);
	}

}
