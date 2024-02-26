package com.core.acceso1.service;

import java.util.List;
import java.util.Optional;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;

public interface IRoleService {
	
	Optional<Role> findOptById(String id);
	
	List<Role> findListAll();
	
	Role save(Role role);
}
