package com.core.acceso1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.data.model.User;
import com.core.acceso1.data.repository.IProductRepository;
import com.core.acceso1.data.repository.IRoleRepository;
import com.core.acceso1.data.repository.IUserRepository;
import com.core.acceso1.service.IProductService;
import com.core.acceso1.service.IRoleService;
import com.core.acceso1.service.IUserService;



@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserRepository repository;
	
	@Override
	public Optional<User> findOptById(String id){
		return repository.findById(id);
	}
	
	@Override
	public List<User> findListAll(){
		return repository.findAll();
	}	

				
	
	@Override
	public User save(User user){
	return repository.save(user);
		}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = this.findOptById(username);
		if (userOpt.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return userOpt.get();
	}
	

}
