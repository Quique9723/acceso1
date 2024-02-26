package com.core.acceso1.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Product;

@Repository
public interface IFamilyProductRepository extends JpaRepository<FamilyProduct, Long> {

	

}
