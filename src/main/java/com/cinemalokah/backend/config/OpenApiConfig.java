package com.cinemalokah.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI cinemaLokahOpenAPI() {

		return new OpenAPI().info(new Info().title("Cinema Lokah API").description("""
				Cinema Lokah - Movie and TV Show Discovery Platform.

				Discover movies, TV shows, cast information,
				genres and OTT streaming availability.
				""").version("v1.0.0").contact(new Contact().name("Cinema Lokah").email("support@cinemalokah.com")));
	}
}
