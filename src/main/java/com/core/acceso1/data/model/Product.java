package com.core.acceso1.data.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Product implements Serializable {
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Product name is required.")
	@Column(nullable = false, columnDefinition = "varchar(225) default 'TBD'")
	private String name;

	@Column(columnDefinition = "double default 0.0")
	private Double price;

	@Column(columnDefinition = "DECIMAL(5,2) default 0 CHECK(discount >=0 and discount <=100)")
	private Double discount;

	@Column(columnDefinition = "integer default 1 CHECK(valuation >=1 and valuation <=5)")
	private Integer valuation;

	@Column(columnDefinition = "integer default 25")
	private Integer stock;

	private String pictureUrl;

	@ManyToOne
	private FamilyProduct familyProduct;

	public Double getFinalPrice() {

		return (getPrice() * ((100 - getDiscount()) / 100));
	}

}
