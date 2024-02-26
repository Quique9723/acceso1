package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Product;

public interface IProductService {
	
	Optional<Product> findOptById(Long id);
	
	List<Product> findListAll();
	
	public List<Product> findListByFamilyProductId(Long idFamilyProduct);
	public Double getPriceAverageByFamilyProductId(Long idFamilyProduct);
	
	Product save(Product product);
}
