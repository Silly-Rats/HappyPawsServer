package org.silly.rats.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers(HttpMethod.GET, "api/ping",
								"api/user/worker/*",
								"api/dog/breeds",
								"api/reserve/training/free/*",
								"api/reserve/training/pass/*",
								"api/category/*/info",
								"api/category/info",
								"api/category/*/attr",
								"api/category/*/img",
								"api/item/*/info",
								"api/item/*/images",
								"api/item/*/image",
								"api/item/type/*/info",
								"api/item/all/id")
						.permitAll()
						.requestMatchers(HttpMethod.POST, "api/auth/register",
								"api/auth/authenticate",
								"api/reserve/training",
								"api/item/*/items")
						.permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("https://happypaws-87hv.onrender.com")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
