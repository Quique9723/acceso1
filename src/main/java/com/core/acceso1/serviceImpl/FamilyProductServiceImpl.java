package com.core.acceso1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.repository.IFamilyProductRepository;
import com.core.acceso1.service.IFamilyProductService;

@Service
public class FamilyProductServiceImpl implements IFamilyProductService{
	@Autowired
	private IFamilyProductRepository repository;
	
	@Override
	public Optional<FamilyProduct> findOptById(Long id){
		return repository.findById(id);
	}
	
	@Override
	public List<FamilyProduct> findListAll(){
		return repository.findAll();
	}	
	@Override
	public FamilyProduct save(FamilyProduct familyProduct){
	return repository.save(familyProduct);
		}	
	
	@Override 
	public Boolean existsById(Long id) {
		return repository.existsById(id);
	
	}
	@Override
	@Secured({"ADMIN"})
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public FamilyProduct newEntity() {
				return new FamilyProduct();
	}
}
