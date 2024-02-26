package com.core.acceso1.data.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.catalina.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderLine implements Serializable  {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	private OrderLinePK pk;
	
	@Column(nullable=false)
	private Integer quantity;
	
	public OrderLine(Order order,Product product,Integer quantity) {
		
		pk= new OrderLinePK();
		pk.setOrder(order);
		pk.setProduct(product);
		this.quantity = quantity;
		
	}
	
	@Transient
	public Product getProduct() {
		return this.pk.getProduct();
	}
	
	@Transient
	public Double getTotalLinePrice() {
		return getProduct().getPrice() * getQuantity();
	}
	
}

    
