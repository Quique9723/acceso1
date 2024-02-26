package com.core.acceso1;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.core.acceso1.data.model.FamilyProduct;
import com.core.acceso1.data.model.Order;
import com.core.acceso1.data.model.OrderLine;
import com.core.acceso1.data.model.OrderLinePK;
import com.core.acceso1.data.model.Product;
import com.core.acceso1.data.model.Role;
import com.core.acceso1.data.model.User;
import com.core.acceso1.service.IFamilyProductService;
import com.core.acceso1.service.IOrderLineService;
import com.core.acceso1.service.IOrderService;
import com.core.acceso1.service.IProductService;
import com.core.acceso1.service.IRoleService;
import com.core.acceso1.service.IUserService;

@SpringBootApplication
public class Acceso1Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Acceso1Application.class, args);
	}
	
	@Bean
	CommandLineRunner runner(IProductService productService, IFamilyProductService familyProductService,IRoleService roleService,IUserService userService,IOrderService orderService,IOrderLineService orderLineService) {
		FamilyProduct familyProduct1 = new FamilyProduct(1L, "TEXTIL",null);
		FamilyProduct familyProduct2 = new FamilyProduct(2L, "ELECTRONICA",null);
		FamilyProduct familyProduct3 = new FamilyProduct(3L, "ALIMENTACION",null);
		FamilyProduct familyProduct4 = new FamilyProduct(4L, "DROGERÍA",null);
		
		Product product1 = new Product(1L, "Drone", 1.23,10.0,1,100, "https://mdbootstrap.com/img/new/ecommerce/horizontal/001.jpg",familyProduct2);
		Product product2 = new Product(2L, "Cámara fotos", 4.56,0.0,2, 200, "https://mdbootstrap.com/img/new/ecommerce/horizontal/025.jpg", familyProduct2);
		Product product3 = new Product(3L, "Mochila", 7.89,0.0,3, 300, "https://mdbootstrap.com/img/new/ecommerce/horizontal/050.jpg", familyProduct1);
		Product product4 = new Product(4L, "Limones", 1.23,0.0,4, 100, "https://mdbootstrap.com/img/new/ecommerce/horizontal/084.jpg",familyProduct3);
		Product product5 = new Product(5L, "Almendras", 4.56,0.0,5,200, "https://mdbootstrap.com/img/new/ecommerce/horizontal/085.jpg", familyProduct3);
		Product product6 = new Product(6L, "Pimientos", 7.89,0.0,1,300, "https://mdbootstrap.com/img/new/ecommerce/horizontal/086.jpg", familyProduct3);
		Product product7 = new Product(7L, "Colonia", 1.23,0.0,2,100, "https://mdbootstrap.com/img/new/ecommerce/horizontal/087.jpg",familyProduct4);
		Product product8 = new Product(8L, "Vestido", 4.56,0.0,3,200, "https://mdbootstrap.com/img/new/ecommerce/horizontal/088.jpg", familyProduct1);
		Product product9 = new Product(9L, "Zapatos", 7.89,0.0,4,300, "https://mdbootstrap.com/img/new/ecommerce/horizontal/089.jpg", familyProduct1);
		Product product10 = new Product(10L, "Mouse", 7.89,0.0,5,300, "https://mdbootstrap.com/img/new/ecommerce/horizontal/111.jpg", familyProduct2);
		
		Role role1= new Role("USER");
		Role role2= new Role("MANAGER");
		Role role3= new Role("ADMIN");
		Role role4= new Role("CUSTOMER");
		
		User user1 = new User("ana", "$2a$12$FnYgANwXz4fewUboItszeudf/tBxqy10r0XJBc.FWwkUDn.HCV.qC", "ana@gmail.com", "Ana Perez", LocalDate.now().plusMonths(3), false, LocalDate.now().plusMonths(2), true, Set.of(role1));
		User user2 = new User("luis", "$2a$12$FnYgANwXz4fewUboItszeudf/tBxqy10r0XJBc.FWwkUDn.HCV.qC", "luis@gmail.com", "Luis Sanchez", LocalDate.now().plusMonths(3), false, LocalDate.now().plusMonths(2), true, Set.of(role1, role3));
		User user3 = new User("cliente1", "$2a$12$LfQ2POVSk3GAgC5QkB16oOLPnP7vGq3Y3u/yu52m97oq0daGVoo5a", "cliente1@gmail.com", "Cliente Sanchez", LocalDate.now().plusMonths(3), false, LocalDate.now().plusMonths(2), true, Set.of(role4));
		Order order1 = new Order(1L,LocalDate.now(),"pagado", null);
		
		OrderLine orderLine1 = new OrderLine(new OrderLinePK(order1,product1),5);
		OrderLine orderLine2 = new OrderLine(new OrderLinePK(order1,product2),6);
		OrderLine orderLine3 = new OrderLine(new OrderLinePK(order1,product3),7);
		
		
		
		return args -> {
			
			familyProductService.save(familyProduct1);
			familyProductService.save(familyProduct2);
			familyProductService.save(familyProduct3);
			familyProductService.save(familyProduct4);
			
			productService.save(product1);
			productService.save(product2);
			productService.save(product3);
			productService.save(product4);
			productService.save(product5);
			productService.save(product6);
			productService.save(product7);
			productService.save(product8);
			productService.save(product9);
			productService.save(product10);
			
			roleService.save(role1);
			roleService.save(role2);
			roleService.save(role3);
			roleService.save(role4);
			
			userService.save(user1);
			userService.save(user2);
			userService.save(user3);
			
			orderService.save(order1);
			
			orderLineService.save(orderLine1);
			orderLineService.save(orderLine2);
			orderLineService.save(orderLine3);
			
			
		};
	}

}

	

