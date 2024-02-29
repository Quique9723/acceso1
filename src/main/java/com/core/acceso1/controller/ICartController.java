package com.core.acceso1.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.core.acceso1.data.model.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface ICartController {
	
	
	
	String requestURI(HttpServletRequest request);

	String cartAddGet(Principal principal, Model model, HttpServletRequest request);

	String cartAddChangePageSizeGet(@PathVariable Integer pageSize,Principal principal, Model model, HttpServletRequest request );

	String changeFirstPageGet(Principal principal, Model model, HttpServletRequest request);

	String changeLastPageGet(Principal principal, Model model, HttpServletRequest request);

	String changePrevPageGet(Principal principal, Model model, HttpServletRequest request);

	String changeNextPageGet(Principal principal, Model model, HttpServletRequest request);

	String changePageGet(Integer numPage, Principal principal, Model model, HttpServletRequest request);
	
		
			

}
