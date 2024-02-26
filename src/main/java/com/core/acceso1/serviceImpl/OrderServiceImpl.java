package com.core.acceso1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.Order;
import com.core.acceso1.data.repository.IOrderRepository;
import com.core.acceso1.service.IOrderService;

@Service

public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository repository;
	
	@Override
	public Order save(Order product) {
		return repository.save(product);
	}

	@Override
	public List<Order> findListAll() {
		return repository.findAll();
	}
	
	public Optional<Order> findById(Long id) {
		return repository.findById(id);
	}

}
