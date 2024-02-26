package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.OrderLine;
import com.core.acceso1.data.model.Product;

public interface IOrderLineService {
	
	public OrderLine save (OrderLine orderLine);
	
	public List<OrderLine> findListAllByPkOrderId(Long id);
	
	
}
