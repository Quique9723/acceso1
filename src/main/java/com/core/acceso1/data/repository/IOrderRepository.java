package com.core.acceso1.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.acceso1.data.model.Order;


@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findListByDateCreated(LocalDate date);
	

}
