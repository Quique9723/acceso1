package com.core.acceso1.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.catalina.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;
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
//@ToString
@Table(name="ORDERS")
public class Order implements Serializable  {
		
		@Getter(AccessLevel.NONE)
		@Setter(AccessLevel.NONE)
		private static final long serialVersionUID = 1L;
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private LocalDate dateCreated;

	    private String status;
	    
	    
	    @OneToMany(mappedBy="pk.order")
	    @Valid
	    @Exclude
	    private List<OrderLine> orderLineList = new ArrayList<>();
	    
	    @Transient
	    public Double getTotalOrderPrice() {
	    	return getOrderLineList().stream()
	    			.map(OrderLine::getTotalLinePrice)
	    			.reduce(0.0,(p1,p2)->p1 + p2);//price and totalPrice
	    			
	    	
//	    	double sum = 0D;
//	    	List<OrderLine> orderLineList = getOrderLineList();
//	    	for (OrderLine op : orderLineList) {
//	    		sum += op.getTotalLinePrice();
//	    	}
//	    	return sum;
	    }
	    
	    @Transient
	    public int getNumberOfProducts() {
	    	return this.orderLineList.size();
	    }
	

}

    
