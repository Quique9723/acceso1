package com.core.acceso1.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.core.acceso1.service.IUserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)

public class WebSecConfig {
	
	@Autowired
	private IUserService userService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
	
	httpSecurity
				.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**"))
				.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/","/index","/login*","/h2/**").permitAll()
					.requestMatchers("/img/**").permitAll()
					.requestMatchers("/welcome").authenticated()
					.anyRequest().authenticated())
				//.formLogin(Customizer.withDefaults())
				
				.formLogin(formLogin -> {
					formLogin
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/welcome")
					.failureUrl("/login?badCredentials");
				})
				
				.logout(formLogout->{
					formLogout
					.logoutUrl("/logout")
					.permitAll()
					.logoutSuccessUrl("/index");
				});
	
	
	return httpSecurity.build();


}
	 @Bean
	    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
	    	AuthenticationManagerBuilder authenticationManagerBuilder = 
	    			httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
	    	authenticationManagerBuilder.authenticationProvider(authenticationProvider());
	    	return authenticationManagerBuilder.build();
	    }
	 @Bean
	    DaoAuthenticationProvider authenticationProvider() {
	    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    	daoAuthenticationProvider.setUserDetailsService(this.userService);
	    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoderBCrypt());
	    	return daoAuthenticationProvider;
	    }
	 @Bean
	 PasswordEncoder passwordEncoderBCrypt() {
		 return new BCryptPasswordEncoder();
	 }
}
