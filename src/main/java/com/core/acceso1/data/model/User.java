package com.core.acceso1.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
import jakarta.persistence.JoinColumn;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="USERS")
@Slf4j
public class User implements Serializable,UserDetails {
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
		
	@Id
    @NotNull(message = "User name is required.")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    @Size(min=1, max=50, message="Campo rolename debe tener entre 3 y 50 caracteres")
    //@Pattern(regexp = "([A-Za-z]{1,1})([A-Za-z0-9_-]{1,49})", message = "Campo rolename debe comenzar por una letra, luego admite número y guiones")
	private String username;
	
	@NotNull 
	@Size(min=8, max=250, message="Campo password debe tener entre 8 y 250 caracteres")
	private String password;
	
	@NotNull 
	@Size(min=3, max=100, message="Campo email debe tener entre 3 y 100 caracteres")
	@Email
	private String email;
	
	@NotNull 
	@Size( max=100, message="Campo fullname debe tener un maximo de 100 caracteres")
	private String fullname;
	
	@NotNull 
	@DateTimeFormat (pattern = "yyyy-MM-dd")
	private LocalDate expiryDateAccount;
	
	@NotNull 
	private Boolean lockedAccount;
	
	@NotNull 
	@DateTimeFormat (pattern = "yyyy-MM-dd")
	private LocalDate expiryDateCredentials;
	
	@NotNull
	private Boolean enabled;
	
	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(
//			name = "USER_ROLE",
//			joinColumns= @JoinColumn(name="FK_USER_USERNAME",referencedColumnName="USERNAME"),
//			inverseJoinColumns = @JoinColumn(name = "FK_ROLE_ROLENAME", referencedColumnName="ROLENAME")
//			
//			)
	private Set<Role> roleSet;

	@Override
	public String toString() {
		return "User [username=" + username 
				+ ", password=" + password + ", email=" + email
				+ ", fullname=" + fullname
				+ ", expiryDateAccount=" + expiryDateAccount 
				+ ", lockedAccount=" + lockedAccount
				+ ", expiryDateCredentials=" + expiryDateCredentials
				+ ", enabled=" + enabled 
				+ ", roleSet=" + roleSet.stream()
				.map(x -> x.getRolename())
				.collect(Collectors.joining(" | ", "{","}"))
				+ "]";
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public boolean isAccountNonExpired() {
		return this.expiryDateAccount.isAfter(LocalDate.now());
		
	}

	@Override
	public boolean isAccountNonLocked() {
	return !this.getLockedAccount();
	}

	@Override
	public boolean isCredentialsNonExpired() {
	return this.expiryDateCredentials.isAfter(LocalDate.now());
	}

	@Override
	public boolean isEnabled() {
		
		return this.getEnabled();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
		// Obtener los roles del usuario autenticado:
		this.getRoleSet().stream()
			.map(x -> x.getRolename())
			.forEach(x -> simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(x)));
		// Mostrar los roles del usuario autenticado:
		log.info("Roles de: " + this.getUsername() + ", " + this.getFullname() + ": " +
			simpleGrantedAuthorityList.stream()
				.map(x -> x.getAuthority())
				.collect(Collectors.joining("|", "{", "}")));
		//		
		return simpleGrantedAuthorityList;
	}

	
	
	
	
//	 @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roleSet")
//	    private Set<User> userSet;
}

    
