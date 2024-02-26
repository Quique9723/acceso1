package com.core.acceso1.data.model;

//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Login {

    @Size(min=3, max=50, message="Campo 'username' debe tener entre 3 y 50 caracteres")
//    @Pattern(regexp = "[A-Za-z0-9_-]{3,50}", message = "Solo se admiten letras, números, _ y -")
	private String username;

    @Size(min=4, max=250, message="Campo 'password' debe tener entre 4 y 250 caracteres")
	private String password;
    
}