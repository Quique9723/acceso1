package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.data.model.User;

public interface IUserService extends UserDetailsService {
	
	Optional<User> findOptById(String id);
	
	List<User> findListAll();
	
	User save(User user);
}
