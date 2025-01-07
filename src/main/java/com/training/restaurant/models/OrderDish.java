package com.training.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_has_dishes")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderDish implements Serializable {

    private static final Long serialVersionUID = 1L;

    @EmbeddedId
    private OrderDishId id = new OrderDishId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private Dishes dish;

    @Column(nullable = false)
    private Integer quantity;

}
