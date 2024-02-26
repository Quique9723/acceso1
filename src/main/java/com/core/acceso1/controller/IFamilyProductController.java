package com.core.acceso1.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface IFamilyProductController {
	
	
	
	
	String requestURI(HttpServletRequest request);
	String FamilyProductListGet(Principal principal, Model model, HttpServletRequest request);	
	String FamilyProductUpdatePost(@Valid FamilyProduct entity, BindingResult bindingResult, Long id,
			Principal principal, Model model, HttpServletRequest request);
	String FamilyProductUpdateGet(Long id, Principal principal, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes);
	String FamilyProductViewGet(Long id, Principal principal, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes);
	String FamilyProductAddGet(Principal principal, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes);
	String FamilyProductAddPost(@Valid FamilyProduct entity, BindingResult bindingResult, Long id, Principal principal,
			Model model, HttpServletRequest request);
	String FamilyProductDeletePost(FamilyProduct entity, Long id, Principal principal, Model model,
			HttpServletRequest request);
	String FamilyProductOperationPost(String operation,Long id, @Valid FamilyProduct entity, BindingResult bindingResult,
			Principal principal, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes);
	String FamilyProductOperationGet(String operation, Long id, Principal principal, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes);
	

		
			

}
