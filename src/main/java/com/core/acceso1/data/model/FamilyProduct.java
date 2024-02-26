package com.core.acceso1.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
//@ToString
public class FamilyProduct implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Family Product description is required.")
	@Size(min=1,max=25, message="Campo descripcion debe tener entre 1 y 25 caracteres")
	@Column(nullable = false, columnDefinition = "varchar(25) default 'TBD'")
	private String description;
	
	//@ToString.Exclude
	@OneToMany(mappedBy = "familyProduct")
	private List<Product> productList = new ArrayList<>();

	public String toString() {
		return "\nFamilyProduct ["
				+ "id=" + id 
				+ ", description=" + description 
				+ ", productList.size()=" + (productList == null ? "EMPTY LIST" : productList.size())
				+ ",productList->names=" + (productList == null ? "EMPTY LIST" :
				(productList.size() > 0
					? productList.stream()
						.map(x -> x.getName())
						.collect(Collectors.joining(", "))
					: "Empty"))
				 
				+ "]";
	
	}

}
