package com.core.acceso1.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.core.acceso1.data.model.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface ICartController {
	
	
	
	String requestURI(HttpServletRequest request);

	String cartAddGet(Principal principal, Model model, HttpServletRequest request);

	
		
			

}
