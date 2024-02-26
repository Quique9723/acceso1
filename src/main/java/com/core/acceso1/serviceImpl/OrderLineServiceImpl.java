package com.core.acceso1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.core.acceso1.data.model.OrderLine;

import com.core.acceso1.data.repository.IOrderLineRepository;

import com.core.acceso1.service.IOrderLineService;

@Service

public class OrderLineServiceImpl implements IOrderLineService {

	@Autowired
	private IOrderLineRepository repository;
	
	@Override
	public OrderLine save(OrderLine orderLine) {
		return repository.save(orderLine);
	}

	@Override
	public List<OrderLine> findListAllByPkOrderId(Long id) {
		return repository.findListAllByPkOrderId(id);
	}

}