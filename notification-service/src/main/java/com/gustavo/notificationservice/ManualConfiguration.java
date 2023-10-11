package com.gustavo.notificationservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

// Configuração manual necessária para que o Observability funcione.

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ManualConfiguration {
	
	private final ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory;

    @PostConstruct
    void setup() {
        this.concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
    }

}
