package com.core.acceso1.controllerImpl;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.core.acceso1.controller.ICartController;
import com.core.acceso1.data.model.Product;
import com.core.acceso1.service.IProductService;
import com.core.acceso1.serviceImpl.PagePropertiesServiceImpl;
import com.core.acceso1.tools.FormGenericPage;

@Controller
@Slf4j
@RequestMapping("/shoppingCart")
public class CartControllerImpl implements ICartController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IProductService productService;
    
    @Autowired
    private PagePropertiesServiceImpl pagePropertiesServiceImpl;

    private FormGenericPage<Product> formPage; 

        
    @PostConstruct
    void init() {
    	this.formPage = new FormGenericPage<Product>(
    			this.pagePropertiesServiceImpl.getPageSizeList(),
    			this.pagePropertiesServiceImpl.getPageSizeList().get(0),
    			this.pagePropertiesServiceImpl.getNumPartials()
    			);

    	
    	
    	this.formPage.setRequestMappingClassString(this.getClass().getAnnotation(RequestMapping.class).value()[0]);

    	
    	
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
// 		model.addAttribute("productList", productService.findListAll());
// 		model.addAttribute("productList", productService.findListPage(PageRequest.of(1, 4)));
 		//
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);
 		//
 		
 		
 		this.formPage.setTotalItems(this.productService.count()); 		

 		
 		//
 		log.info("pagePropertiesServiceImpl.getPageSizeList()= " 
 				+ pagePropertiesServiceImpl.getPageSizeList());
 		log.info("pagePropertiesServiceImpl.getNumPartials()= " 
 				+ pagePropertiesServiceImpl.getNumPartials()); 		
 		//
 		log.info("this.formPage.getPageSizeList()= " + this.formPage.getPageSizeList()); 		
 		log.info("this.formPage.getPageSizeSelected()= " + this.formPage.getPageSizeSelected()); 		
 		log.info("this.formPage.getNumPartialPages()= " + this.formPage.getNumPartials()); 		
 		log.info("this.formPage.getTotalItems()= " + this.formPage.getTotalItems()); 		
 		//
 		this.formPage.changeCurrentPageSize(this.formPage.getPageSizeSelected());
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(1, this.formPage.getPageSizeSelected()))
 				.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		//
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    }
    
    @Override
 	@GetMapping({ "/changePageSize/{pageSizeSelected}", "/changePageSize" })
 	public String cartAddChangePageSizeGet(
 			@PathVariable(name = "pageSizeSelected", required=false) Integer pageSizeSelected,
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA cartAddChangePageSize-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		

 		
 		this.formPage.setTotalItems(this.productService.count());
 		//
 		
 		
    	
 		log.info("request.getContextPath()= " + request.getContextPath());
    	log.info("this.getClass().getAnnotation(RequestMapping.class).value()= "
    			+ this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    	
 		

 		
 		//
 		log.info("this.formPage.getPageSizeList()= " + this.formPage.getPageSizeList()); 		
 		log.info("this.formPage.getPageSizeSelected()= " + this.formPage.getPageSizeSelected()); 		
 		log.info("this.formPage.getNumPartialPages()= " + this.formPage.getNumPartials()); 		
 		log.info("this.formPage.getTotalItems()= " + this.formPage.getTotalItems()); 		
 		//
 		this.formPage.changeCurrentPageSize(pageSizeSelected == null ? this.formPage.getPageSizeSelected() : pageSizeSelected);
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(this.formPage.getCurrentPage(), this.formPage.getPageSizeSelected()))
 				.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changePage-GET controller > this.formPage= " + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    }

    @Override
 	@GetMapping({ "/changeFirstPage" })
 	public String changeFirstPageGet(
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA changeFirstPage-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		//
 		//
 		//
 		//
 		this.formPage.changeFirstGroupPages();
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(
 				1, 
 				this.formPage.getPageSizeSelected())) 					
 					.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changeFirstPage-GET controller" + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    } 		

    @Override
 	@GetMapping({ "/changeLastPage" })
 	public String changeLastPageGet(
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA changeLastPage-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		//
 		//
 		//
 		//
 		this.formPage.changeLastGroupPages();
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(
 				this.formPage.getCurrentPage(), 
 				this.formPage.getPageSizeSelected())) 					
 					.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changeLastPage-GET controller" + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    } 		

    @Override
 	@GetMapping({ "/changePrevPage" })
 	public String changePrevPageGet(
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA changePrevPage-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		//
 		//
 		//
 		//
 		this.formPage.changePrevGroupPages();
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(
 				this.formPage.getCurrentPage(), 
 				this.formPage.getPageSizeSelected())) 					
 					.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changePrevPage-GET controller" + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    } 		

    @Override
 	@GetMapping({ "/changeNextPage" })
 	public String changeNextPageGet(
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA changeNextPage-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		//
 		//
 		//
 		//
 		this.formPage.changeNextGroupPages();
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(
 				this.formPage.getCurrentPage(), 
 				this.formPage.getPageSizeSelected())) 					
 					.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changeNextPage-GET controller" + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    } 		

    @Override
 	@GetMapping({ "/changePage/{numPage}" })
 	public String changePageGet(
 			@PathVariable("numPage") Integer numPage,
 			Principal principal, 
 			Model model, 
 			HttpServletRequest request) {
 		log.info("TRAZA changePage-GET controller");	
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
		ResourceBundle valuationBundle = ResourceBundle.getBundle("i18n/message", 
					LocaleContextHolder.getLocale());
		List<String> valuationTitleStringArray = Arrays.asList(valuationBundle
				.getString("html.orderLineDialectAddToCart.valuationTitleStringArray").split(","));
 		model.addAttribute("valuationTitleStringArray", valuationTitleStringArray);	
 		//
 		//
 		//
 		//
 		//
 		//
 		this.formPage.changeCurrentPage(numPage);
 		this.formPage.getFormGenericPiece()
 			.setItemList(productService.findListPage(this.formPage.calculatePageRequest(
 				this.formPage.getCurrentPage(), 
 				this.formPage.getPageSizeSelected())) 					
 					.getContent());		
 		model.addAttribute("formPage", this.formPage);
 		log.info("TRAZA changePage-GET controller > numPage= " + numPage + "\nthis.formPage= " + this.formPage);	
 		//
 		return "dialectLayouts/centrals/orderLineDialectAddToCart";
    } 		

    @Override
    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
       return request.getRequestURI();
    }    

}