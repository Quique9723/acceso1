package com.core.acceso1.config;




import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;



@Configuration

public class WebMvcConfig implements WebMvcConfigurer {
	
         //bean to change Locale by default.
	 @Bean
	 LocaleResolver localeResolver() {
		 
		 SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		 sessionLocaleResolver.setDefaultLocale(Locale.US);
		 
		 
		 return sessionLocaleResolver;
		 
		//bean to apply an interceptor for changing Locale from web pages.		
	 }
	 @Bean 
	 LocaleChangeInterceptor localeChangeInterceptor() {
		 LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		 localeChangeInterceptor.setParamName("lang");
		 return localeChangeInterceptor;
		 
	 }
	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(localeChangeInterceptor());
	 }
	 
	 @Bean
	 ResourceBundleMessageSource messageSource() {
		 ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		// rbms.setBasename("i18n/message");
		 rbms.setBasenames("i18n/message","i18n/format");
		 rbms.setDefaultEncoding("UTF-8");
		 return rbms;
	 }
}
