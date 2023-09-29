package com.gustavo.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
