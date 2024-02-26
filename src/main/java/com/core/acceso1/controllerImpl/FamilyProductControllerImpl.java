package com.core.acceso1.controllerImpl;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.core.acceso1.controller.IFamilyProductController;
import com.core.acceso1.controller.IStartController;
import com.core.acceso1.controller.IStartLoggedController;
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
public class FamilyProductControllerImpl implements IFamilyProductController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private IFamilyProductService familyProductService;

	@ModelAttribute("requestURI")
	public String requestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	@Override
	@GetMapping(value = { "/startLoggedServicesFamilyProduct" })
	public String FamilyProductListGet(Principal principal, Model model, HttpServletRequest request) {
		log.info("TRAZA startLoggedServicesFamilyProduct-GET controller");
		// Inyectamos el usuario en la página
		model.addAttribute("username", principal.getName());
		// Inyectar la lista de roles
		model.addAttribute("authoritySet", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities()
				.stream().map(x -> x.toString()).collect(Collectors.toSet()));

		model.addAttribute("familyProductList", familyProductService.findListAll());
		return "dialectLayouts/centrals/familyProductDialectList";
	}

	@Override

	@GetMapping({ "/familyProduct/view/{id}" })
	public String FamilyProductViewGet(@PathVariable("id") Long id, Principal principal, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		log.info("TRAZA FamilyProductViewGet: id= " + id + " > " + this.familyProductService.findOptById(id).get());

		Optional<FamilyProduct> familyProductOpt = this.familyProductService.findOptById(id);

		if (familyProductOpt.isEmpty()) {

			redirectAttributes.addFlashAttribute("flashMessage", "Registro inexistente");

			return "redirect:/startLoggedServicesFamilyProduct";

		} else {

			model.addAttribute("flashMessage", "Registro encontrado.");
			model.addAttribute("entity", familyProductOpt.get());
			return "dialectLayouts/centrals/familyProductDialectView";
		}

		// return "dialectLayouts/centrals/familyProductDialectView";
	}

	@Override

	@GetMapping({ "/familyProduct/update/{id}" })
	public String FamilyProductUpdateGet(@PathVariable("id") Long id, Principal principal, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		log.info("TRAZA FamilyProductUpdateGet: id= " + id + " > " + this.familyProductService.findOptById(id).get());

		Optional<FamilyProduct> familyProductOpt = this.familyProductService.findOptById(id);

		if (familyProductOpt.isEmpty()) {

			redirectAttributes.addFlashAttribute("flashMessage", "Registro inexistente");
			return "redirect:/startLoggedServicesFamilyProduct";

		} else {
			model.addAttribute("flashMessage", "Registro encontrado.");
			model.addAttribute("entity", familyProductOpt.get());
			return "dialectLayouts/centrals/familyProductDialectUpdate";

		}
	}

	@Override
	@GetMapping({ "/familyProduct/add" })
	public String FamilyProductAddGet(Principal principal, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		log.info("TRAZA FamilyProductAddGet");
		model.addAttribute("flashMessage", "Registro nuevo.");
		model.addAttribute("entity", this.familyProductService.newEntity());
		return "dialectLayouts/centrals/familyProductDialectAdd";
	}
	// return "dialectLayouts/centrals/familyProductDialectView";

	@Override
	@GetMapping({ "/familyProductOperation/{operation}/{id}",
					"/familyProductOperation/{operation}"})
	public String FamilyProductOperationGet(@PathVariable("operation") String operation, @PathVariable(name ="id", required=false) Long id,
			Principal principal, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		log.info("TRAZA FamilyProductperationGet");
		Optional<FamilyProduct> familyProductOpt = null;
		model.addAttribute("operation", operation);
		if(id != null) model.addAttribute("identifier", id);
		String returned;

		switch (operation) {

		case "delete":
			familyProductOpt = this.familyProductService.findOptById(id);
			if (familyProductOpt.isEmpty()) {
				log.info("TRAZA FamilyProductperationGet IF");
				redirectAttributes.addFlashAttribute("flashMessage", "Registro inexistente");
				returned = "redirect:/startLoggedServicesFamilyProduct";
			} else {
				log.info("TRAZA FamilyProductperationGet else");
				model.addAttribute("flashMessage", "Registro borrado");
				model.addAttribute("entity", familyProductOpt.get());
				returned = "dialectLayouts/centrals/familyProductDialectOperation";
			}
			break;
		case "view":
			familyProductOpt = this.familyProductService.findOptById(id);
			if (familyProductOpt.isEmpty()) {
				log.info("TRAZA Famil");
				model.addAttribute("flashMessage", "registro inexistente");
				returned = "redirect:/startLoggedServicesFamilyProduct";
			} else {
				log.info("TRAZA FamilyProductOperationGet-view: id= " + id + "> isPresent()" + familyProductOpt.get());
				model.addAttribute("flashMessage", "registro encontrado");
				model.addAttribute("entity", familyProductOpt.get());
				returned = "dialectLayouts/centrals/familyProductDialectOperation";
			}
			break;
		case "update":
			familyProductOpt = this.familyProductService.findOptById(id);

			if (familyProductOpt.isEmpty()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Registro inexistente");
				returned = "redirect:/startLoggedServicesFamilyProduct";

			} else {
				model.addAttribute("flashMessage", "Registro encontrado.");
				model.addAttribute("entity", familyProductOpt.get());
				returned = "dialectLayouts/centrals/familyProductDialectOperation";
			}
			break;
		case "add":
			familyProductOpt = Optional.of(familyProductService.newEntity());
			model.addAttribute("flashMessage", "registro nuevo");
			model.addAttribute("entity", familyProductOpt.get());
			returned = "dialectLayouts/centrals/familyProductDialectOperation";
			break;
		default:
			log.error("TRAZA mal");
			redirectAttributes.addFlashAttribute("flashMessage", "OPERATION NOT IDENTIFY");
			returned = "redirect:/startLoggedServicesFamilyProduct";
			break;
		}
		return returned;
	}

	@Override
	@PostMapping({ "/familyProductOperation/{operation}/{identifier}",
					"/familyProductOperation/{operation}"})
	public String FamilyProductOperationPost(@PathVariable("operation") String operation,@PathVariable(name="identifier", required=false) Long identifier,
			@Valid @ModelAttribute("entity") FamilyProduct entity, BindingResult bindingResult, Principal principal,
			Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		log.info("TRAZA FamilyProductperationGet");
		model.addAttribute("operation", operation);
		
		String returned;

		switch (operation) {

		case "delete":
			this.familyProductService.deleteById(entity.getId());
			model.addAttribute("flashMessage", "Registro borrado.");
			returned = "redirect:/startLoggedServicesFamilyProduct";
		
			break;
		case "update":
			if (bindingResult.hasErrors()) {
				log.info("Hay errores de validacion(FamilyProductOperationPost-update): " + bindingResult.getAllErrors());
				model.addAttribute("entity", entity);
				model.addAttribute("flashMessage", "Errores de validacion.");
				returned= "/dialectLayouts/centrals/familyProductDialectOperation";
			} else {

				FamilyProduct familyProductSaved = this.familyProductService.save(entity);
				model.addAttribute("flashMessage", "Registro guardado.");
				returned = "redirect:/startLoggedServicesFamilyProduct";
			}
			break;
		case "add":
			if (bindingResult.hasErrors()) {
				log.info("Hay errores de validacion(FamilyProductOperationPost-add) " + bindingResult.getAllErrors());
				model.addAttribute("entity", entity);
				model.addAttribute("flashMessage", "Errores de validacion.");
				returned = "/dialectLayouts/centrals/familyProductDialectOperation";
			} else {
				log.info("pasa por guardado");
				FamilyProduct familyProductSaved = this.familyProductService.save(entity);
				model.addAttribute("flashMessage", "Registro guardado.");
				returned = "redirect:/startLoggedServicesFamilyProduct";

			}
			break;
		default:
			log.error("TRAZA mal");
			redirectAttributes.addFlashAttribute("flashMessage", "OPERATION NOT IDENTIFY");
			returned = "redirect:/startLoggedServicesFamilyProduct";
			break;
		}
		return returned;
	}

	@GetMapping({ "/familyProduct/delete/{id}" })
	public String FamilyProductDeleteGet(@PathVariable("id") Long id, Principal principal, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		log.info("TRAZA FamilyProductUpdateGet: id= " + id + " > " + this.familyProductService.findOptById(id).get());

		Optional<FamilyProduct> familyProductOpt = this.familyProductService.findOptById(id);

		if (familyProductOpt.isEmpty()) {

			redirectAttributes.addFlashAttribute("flashMessage", "Registro ya borrado");
			return "redirect:/startLoggedServicesFamilyProduct";

		} else {
			model.addAttribute("flashMessage", "Registro encontrado.");
			model.addAttribute("entity", familyProductOpt.get());
			return "dialectLayouts/centrals/familyProductDialectDelete";

		}
	}

	@Override
	@PostMapping({ "/familyProduct/update" })
	public String FamilyProductUpdatePost(@Valid @ModelAttribute("entity") FamilyProduct entity,
			BindingResult bindingResult, Long id, Principal principal, Model model, HttpServletRequest request) {

		log.info("TRAZA FamilyProductUpdatePost: entity = " + entity + " > ");

		if (bindingResult.hasErrors()) {
			log.info("Hay errores de validacion " + bindingResult.getAllErrors());
			model.addAttribute("entity", entity);
			model.addAttribute("flashMessage", "Errores de validacion.");
			return "/dialectLayouts/centrals/familyProductDialectUpdate";
		} else {

			FamilyProduct familyProductSaved = this.familyProductService.save(entity);
			model.addAttribute("flashMessage", "Registro guardado.");
			return "redirect:/startLoggedServicesFamilyProduct";

		}
	}

	@Override
	@PostMapping({ "/familyProduct/add" })
	public String FamilyProductAddPost(@Valid @ModelAttribute("entity") FamilyProduct entity,
			BindingResult bindingResult, Long id, Principal principal, Model model, HttpServletRequest request) {

		log.info("TRAZA FamilyProductAddPost: entity = " + entity + " > ");

		if (bindingResult.hasErrors()) {
			log.info("Hay errores de validacion " + bindingResult.getAllErrors());
			model.addAttribute("entity", entity);
			model.addAttribute("flashMessage", "Errores de validacion.");
			return "/dialectLayouts/centrals/familyProductDialectAdd";
		} else {

			FamilyProduct familyProductSaved = this.familyProductService.save(entity);
			model.addAttribute("flashMessage", "Registro guardado.");
			return "redirect:/startLoggedServicesFamilyProduct";

		}
	}

	@Override
	@PostMapping({ "/familyProduct/delete" })
	public String FamilyProductDeletePost(FamilyProduct entity, Long id, Principal principal, Model model,
			HttpServletRequest request) {

		log.info("TRAZA FamilyProductDeletePost: entity = " + entity + " > ");

		this.familyProductService.deleteById(entity.getId());
		model.addAttribute("flashMessage", "Registro borrado.");
		return "redirect:/startLoggedServicesFamilyProduct";

	}

}
