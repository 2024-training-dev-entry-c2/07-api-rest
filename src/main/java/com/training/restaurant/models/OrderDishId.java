package com.training.restaurant.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class OrderDishId implements Serializable {
    private Long orderId;
    private Long dishId;

}
