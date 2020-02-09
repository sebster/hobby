package com.sebster.repository.jpa.orders;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import com.sebster.repository.api.orders.Order;

public interface JpaOrderAdapter<T> {

	boolean canAdapt(Order<?> order);

	List<javax.persistence.criteria.Order> adapt(Order<T> order, Root<? extends T> root, CriteriaBuilder cb);

}
