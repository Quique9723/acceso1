package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Order;
import com.core.acceso1.data.model.Product;

public interface IOrderService {
	
	public Order save (Order product);
	
	public List<Order> findListAll();
	
	public Optional<Order> findById(Long id);
}
