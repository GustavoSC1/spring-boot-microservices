package com.gustavo.notificationservice;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceApplication {
	
	private final ObservationRegistry observationRegistry;
    private final Tracer tracer;
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	
	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		 Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
	            log.info("Got message <{}>", orderPlacedEvent);
	            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
	                    orderPlacedEvent.getOrderNumber());
	        });
		// enviar uma notificação por e-mail
	}

}
