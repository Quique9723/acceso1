package com.core.acceso1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.data.repository.IProductRepository;
import com.core.acceso1.data.repository.IRoleRepository;
import com.core.acceso1.service.IProductService;
import com.core.acceso1.service.IRoleService;



@Service
public class RoleServiceImpl implements IRoleService{
	@Autowired
	private IRoleRepository repository;
	
	@Override
	public Optional<Role> findOptById(String id){
		return repository.findById(id);
	}
	
	@Override
	public List<Role> findListAll(){
		return repository.findAll();
	}	

				
	
	@Override
	public Role save(Role role){
	return repository.save(role);
		}

	
	
	

}
