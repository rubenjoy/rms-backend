package com.mitrais.rms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class EmployeeRestConfiguration
{
	@Bean
	public FilterRegistrationBean corsFilter()
	{
		UrlBasedCorsConfigurationSource source = 
			new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3003");
		config.addAllowedHeader("*");
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.PATCH);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.HEAD);
		config.addAllowedMethod(HttpMethod.DELETE);
		source.registerCorsConfiguration("/rms/api/**/*", config);
		FilterRegistrationBean bean = 
			new FilterRegistrationBean(
				new CorsFilter(source)
			);
		bean.setOrder( 0 );
		return bean;
	}
}