package com.core.acceso1.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.acceso1.data.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findListByName (String name);
	
	public List<Product> findListByFamilyProductId(Long idFamilyProduct);

}
