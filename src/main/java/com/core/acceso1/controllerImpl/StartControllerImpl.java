package com.core.acceso1.controllerImpl;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.core.acceso1.controller.IStartController;
import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Login;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.service.IFamilyProductService;
import com.core.acceso1.service.ILoginService;
import com.core.acceso1.service.IProductService;
import com.core.acceso1.service.IRoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StartControllerImpl implements IStartController {
	@Autowired
	private IProductService productService;

	@Autowired
	private IFamilyProductService familyProductService;

	// @Autowired
	// private ISqlScriptCreatorService sqlScriptCreatorServiceImpl;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private ILoginService loginService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	protected UserDetailsService userDetailsService;

	@Override
	@GetMapping({ "/login*", "/loginGet*" })
	public String loginGet(Model model, HttpServletRequest request) throws InterruptedException {

		log.info("TRAZA login controller get");

		model.addAttribute("login", loginService.newEntity());
		log.info("Lista de Product: " + productService.findListAll());
		log.info("Lista de FamilyProduct: " + familyProductService.findListAll());
//		log.info("Media de Family id=4: " + productServiceImpl.getPriceAverageByFamilyProductId(4L));
//		model.addAttribute("priceAverage",productServiceImpl.getPriceAverageByFamilyProductId(4L));
//		model.addAttribute("productList",productServiceImpl.findListAll());
//		model.addAttribute("familyProductList",familyProductServiceImpl.findListAll());

		return "standardLayouts/login";

	}
	
	
	

	@Override
	@GetMapping({ "/welcomeMyTestsGet" })
	public String welcomeMyTestsGet(Model model, HttpServletRequest request) throws InterruptedException {
		log.info("TRAZA welcomeMyTests-GET controller");
		//
		FamilyProduct familyProduct = familyProductService.save(new FamilyProduct(null, "PAPELERIA",null));
		log.info("TRAZA Creado nuevo FamilyProduct > " + familyProduct);
		Long idNewFamilyProduct = familyProduct.getId();
		//
		Role role = roleService.save(new Role("CEO"));
		log.info("TRAZA Creado nuevo role > " + role);
		//
		familyProductService.deleteById(idNewFamilyProduct);
		log.info("Existe un FamilyProduct con id=idNewFamilyProduct? > "
				+ (familyProductService.existsById(idNewFamilyProduct) ? "VERDADERO" : "FALSO"));
		//
		log.info("Lista de Product: " + productService.findListAll());
		//
		return "redirect:/indexGet";
	}

	@Override
	@PostMapping(value = { "/loginPost" })
	public String loginPost(@Valid Login login, BindingResult bindingResult, // COMPROBACION DEL LOS DATOS DEL LOGIN Y
																				// SUS REQUISITOS
			Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA: método login-POST");
		String returned = "";
		if (bindingResult.hasErrors()) {
			log.info("HAY ERRORES DE VALIDACIÓN: " + bindingResult.getAllErrors());
			returned = "redirect:/login?malformedCredentials";
		} else {
			try {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						login.getUsername(), login.getPassword());
				Authentication authentication = authenticationManager.authenticate(authenticationToken);
				if (authentication.isAuthenticated()) {
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authentication);
					//
					HttpSession session = request.getSession(true);
					session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
					//
					log.info("TRAZA: login-Post > Session Id= " + request.getSession().getId() + " LOGADO! "
							+ SecurityContextHolder.getContext().getAuthentication().toString());
					returned = "redirect:/welcomeGet";
				} else {
					log.info("TRAZA: login-Post > SIN LOGAR!");
					returned = "redirect:/login?badCredentials";
				}
			} catch (BadCredentialsException e) {
				log.info("BadCredentialsException: " + e.getMessage());
				returned = "redirect:/login?badCredentials";

			} catch (LockedException e) {
				log.info("LockedException: " + e.getMessage());
				returned = "redirect:/login?lockedAccount";

			} catch (DisabledException e) {
				log.info("DisabledException: " + e.getMessage());
				returned = "redirect:/login?disabledAccount";

			} catch (CredentialsExpiredException e) {
				log.info("CredentialsExpiredException: " + e.getMessage());
				returned = "redirect:/login?credentialsExpired";

			} catch (AccountExpiredException e) {
				log.info("AccountExpiredException: " + e.getMessage());
				returned = "redirect:/login?accountExpired";

			} catch (Exception e) {
				log.info("Exception: " + e.getMessage());
				e.printStackTrace();
				returned = "redirect:/login?loginError";

			}
		}
		return returned;
	}

	@Override
	@GetMapping(value = { "/logoutGet" })
	public String logoutGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA logout-GET controller");
		// Inyectamos el usuario en la página
		model.addAttribute("username", principal.getName());
		// Inyectar la lista de roles
		model.addAttribute("authoritySet", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities()
				.stream().map(x -> x.toString()).collect(Collectors.toSet()));

		return "standardLayouts/logout";
	}

	@Override
	@PostMapping(value = { "/logoutPost" })
	public String logoutPost(Model model, HttpServletRequest request) {
		log.info("TRAZA: método logout-POST");
		// INVALIDAMOS LA SESION
		request.getSession().invalidate();

		return "redirect:/login?logoutOK";
	}

	@Override
	@GetMapping(value = { "/", "", "/index", "/indexGet" })
	public String indexGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA index controller");
//		// Inyectamos el usuario en la página
//		model.addAttribute("username", principal.getName());
//		// Inyectar la lista de roles
//		model.addAttribute("authoritySet", 
//			userDetailsService
//			.loadUserByUsername(principal.getName())
//			.getAuthorities()
//			.stream()
//			.map(x -> x.toString())
//			.collect(Collectors.toSet())
////			);		
//		model.addAttribute("priceAverage",productServiceImpl.getPriceAverageByFamilyProductId(4L));
//		model.addAttribute("productList",productServiceImpl.findListAll());
//		model.addAttribute("familyProductList",familyProductServiceImpl.findListAll());

		return "index";
	}

	@Override
	@GetMapping(value = { "/welcomeGet" })
	public String welcomeGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA index controller");
		// Inyectamos el usuario en la página
		model.addAttribute("username", principal.getName());
		// Inyectar la lista de roles
		model.addAttribute("authoritySet", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities()
				.stream().map(x -> x.toString()).collect(Collectors.toSet()));

//		model.addAttribute("priceAverage",productServiceImpl.getPriceAverageByFamilyProductId(4L));
//		model.addAttribute("productList",productServiceImpl.findListAll());
//		model.addAttribute("familyProductList",familyProductServiceImpl.findListAll());

		return "standardLayouts/welcome";
	}

}
