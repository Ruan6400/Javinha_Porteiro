package com.tavolaquad.porteiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.tavolaquad.porteiro.Utilitarios.JwtFiltro;

@SpringBootApplication
public class PorteiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorteiroApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFiltro> jwtFilter(){
		FilterRegistrationBean<JwtFiltro> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new JwtFiltro());
		registrationBean.addUrlPatterns("/protected/*");
		return registrationBean;
	}

}
