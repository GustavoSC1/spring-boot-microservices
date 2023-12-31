package com.gustavo.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.gustavo.orderservice.dto.InventoryResponse;
import com.gustavo.orderservice.dto.OrderLineItemsDto;
import com.gustavo.orderservice.dto.OrderRequest;
import com.gustavo.orderservice.event.OrderPlacedEvent;
import com.gustavo.orderservice.model.Order;
import com.gustavo.orderservice.model.OrderLineItems;
import com.gustavo.orderservice.repository.OrderRepository;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	private final ObservationRegistry observationRegistry;
		
	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
		
		order.setOrderLineItemsList(orderLineItems);

		List<String> skuCodes = order.getOrderLineItemsList().stream()
				.map(OrderLineItems::getSkuCode)
				.toList();

		 Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
	                this.observationRegistry);
	        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
	        return inventoryServiceObservation.observe(() -> {
	            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
	                    .uri("http://inventory-service/api/inventory",
	                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
	                    .retrieve()
	                    .bodyToMono(InventoryResponse[].class)
	                    .block();
	            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
	                    .allMatch(InventoryResponse::isInStock);
	            if (allProductsInStock) {
	                orderRepository.save(order);
	                // publish Order Placed Event
	                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
	    			return "Order Placed Sucessfully";
	            } else {
	                throw new IllegalArgumentException("Product is not in stock, please try again later");
	            }
	        });
	}
	
	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItems.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
