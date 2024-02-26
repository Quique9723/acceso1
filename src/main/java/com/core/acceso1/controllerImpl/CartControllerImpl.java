package com.core.acceso1.controllerImpl;

import java.security.Principal;
import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.core.acceso1.controller.ICartController;
import com.core.acceso1.controller.IStartLoggedController;
import com.core.acceso1.service.IProductService;

@Controller
@Slf4j
public class CartControllerImpl implements ICartController {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private IProductService productService;
    
    


    @Override
    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
       return request.getRequestURI();
    }
    
    @Override
	@GetMapping({ "/cartAdd" })
	public String cartAddGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA cartAdd-GET controller");		
		//
		// Inyectamos el usuario en la página
		model.addAttribute("username", principal.getName());
		// Inyectar la lista de roles
		model.addAttribute("authoritySet", 
			userDetailsService
			.loadUserByUsername(principal.getName())
			.getAuthorities()
			.stream()
			.map(x -> x.toString())
			.collect(Collectors.toSet())
			);
		//
		
		model.addAttribute("productList",productService.findListAll());	
		
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message",
				LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
		model.addAttribute("valuationTitleStringArray",valuationTitleStringArray);
		
//		return "standardLayouts/standardLayoutStartLogged";
		return "dialectLayouts/centrals/orderLineDialectAddToCart";
	}
        
}