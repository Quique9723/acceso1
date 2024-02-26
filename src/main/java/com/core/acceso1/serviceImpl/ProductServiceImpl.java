package com.core.acceso1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.repository.IProductRepository;
import com.core.acceso1.service.IProductService;



@Service
public class ProductServiceImpl implements IProductService{
	@Autowired
	private IProductRepository repository;
	
	@Override
	public Optional<Product> findOptById(Long id){
		return repository.findById(id);
	}
	
	@Override
	public List<Product> findListAll(){
		return repository.findAll();
	}	
	@Override
	public List<Product> findListByFamilyProductId(Long idFamilyProduct) {
		return repository.findListByFamilyProductId(idFamilyProduct);
		
	}

	@Override
	public Double getPriceAverageByFamilyProductId(Long idFamilyProduct) {
		List<Product> productList = this.findListByFamilyProductId(idFamilyProduct);
		
		Double sumPrice = 0.0;
		for (Product product : productList) {
			sumPrice += product.getPrice();
			
		}
		
		
		
		return (productList.size()==0)
				? null
				: sumPrice / productList.size();
		
	}	
	
	@Override
	public Product save(Product product){
	return repository.save(product);
		}

	
	
	

}
