package com.core.acceso1.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.core.acceso1.data.model.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface IStartController {
	
	public String loginGet(Model model,HttpServletRequest request) throws InterruptedException;
	
	String loginPost(
		@Valid Login login,
		BindingResult bindingResult,
		Principal principal,
		Model model,
		HttpServletRequest request);
	
	
	
	
	String logoutGet(Principal principal, Model model, HttpServletRequest request);
	
	String logoutPost(Model model, HttpServletRequest request);
	
	String indexGet(Principal principal, Model model, HttpServletRequest request);

	String welcomeGet(Principal principal, Model model, HttpServletRequest request);

	String welcomeMyTestsGet(Model model, HttpServletRequest request) throws InterruptedException;

	
		
			

}
