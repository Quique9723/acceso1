package com.core.acceso1.data.model;

import java.io.Serializable;
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

public class Role implements Serializable  {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	
	private static final long serialVersionUID = 1l;

	

		
	@Id
    @NotNull(message = "rolename is required.")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    @Size(min=1, max=50, message="Campo rolename debe tener entre 1 y 50 caracteres")
    //@Pattern(regexp = "([A-Za-z]{1,1})([A-Za-z0-9_-]{1,49})", message = "Campo rolename debe comenzar por una letra, luego admite número y guiones")
	private String rolename;
	
//	 @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roleSet")
//	    private Set<User> userSet;
}

    
