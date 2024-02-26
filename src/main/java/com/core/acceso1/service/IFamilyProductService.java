package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import com.core.acceso1.data.model.FamilyProduct;

public interface IFamilyProductService {
	
	Optional<FamilyProduct> findOptById(Long id);
	
	List<FamilyProduct> findListAll();
	
	FamilyProduct save(FamilyProduct familyProduct);

	Boolean existsById(Long id);	

	
	void deleteById(Long idNewFamilyProduct);

	FamilyProduct newEntity();
		
	
}
