package com.gustavo.orderservice.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OrderPlacedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    
	private String orderNumber;

    public OrderPlacedEvent(Object source, String orderNumber) {
        super(source);
        this.orderNumber = orderNumber;
    }

    public OrderPlacedEvent(String orderNumber) {
        super(orderNumber);
        this.orderNumber = orderNumber;
    }
}
