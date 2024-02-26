package com.core.acceso1.controllerImpl;

import java.security.Principal;
import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
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

import com.core.acceso1.controller.IStartLoggedController;

@Controller
@Slf4j
public class StartLoggedControllerImpl implements IStartLoggedController {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
       return request.getRequestURI();
    }
    
    @Override
	@GetMapping({ "/startLogged" })
	public String startLoggedGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA startLogged-GET controller");		
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
		// Inyectamos un mensaje formateado en la página
		// Primero obtenemos el formato:		
		ResourceBundle formatBundle = ResourceBundle.getBundle("i18n/format", LocaleContextHolder.getLocale());
		MessageFormat messageFormatter = new MessageFormat(formatBundle.getString("label"), LocaleContextHolder.getLocale());
		String formattedMessage = messageFormatter.format(new Object[] {Date.valueOf(LocalDate.now()), "Ibai", 2, 1234.56});
		model.addAttribute("numberOfProducts",2);
		model.addAttribute("formattedMessage", formattedMessage);
		model.addAttribute("requestGetLocale", request.getLocale().getDisplayName());
		model.addAttribute("sessionContextLocale", LocaleContextHolder.getLocale().getDisplayName());
		
//		return "standardLayouts/standardLayoutStartLogged";
		return "dialectLayouts/dialectLayoutCentral";
	}
        
}