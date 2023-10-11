package com.gustavo.orderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

//Configuração manual necessária para que o Observability funcione.

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ManualConfiguration {
	
	private final KafkaTemplate kafkaTemplate;

    @PostConstruct
    void setup() {
        this.kafkaTemplate.setObservationEnabled(true);
    }

}
