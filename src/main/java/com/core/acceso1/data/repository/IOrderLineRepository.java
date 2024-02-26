package com.core.acceso1.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.acceso1.data.model.Order;
import com.core.acceso1.data.model.OrderLine;


@Repository
public interface IOrderLineRepository extends JpaRepository<OrderLine, Long> {
	
	public List<OrderLine> findListAllByPkOrderId(Long id);
	
	
	
	

}
