package com.gustavo.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
// É necessário porque o projeto Spring Cloud Gateway é baseado no Spring WebFlux
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity
				.csrf(csrf -> csrf.disable())
				.authorizeExchange(exchange -> 
						exchange.pathMatchers("/eureka/**")
						.permitAll()
						.anyExchange()
						.authenticated())
				// Configuração para microsserviços reativos que informa ao framework Spring Security que a aplicação 
				// Spring Boot vai atuar como um oauth2ResourceServer com o JSON Web Token (JWT).
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));
				
		return serverHttpSecurity.build();
	}

}
