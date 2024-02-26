package com.core.acceso1.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.data.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
	

}
