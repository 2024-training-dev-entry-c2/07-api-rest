package com.training.restaurant.events;

import com.training.restaurant.models.Orders;
import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class NewOrderEvent extends ApplicationEvent {
    private Orders order;

    public NewOrderEvent(Object source, Orders order) {
        super(source);
        this.order = order;
    }
}