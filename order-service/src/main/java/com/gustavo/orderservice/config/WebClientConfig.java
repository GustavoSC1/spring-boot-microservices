package com.gustavo.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	@LoadBalanced // Adiciona os recursos de balanceamento de carga do lado do cliente
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	
}
