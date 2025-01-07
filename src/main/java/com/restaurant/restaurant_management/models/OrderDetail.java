package com.restaurant.restaurant_management.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER_DETAIL")
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Double subtotal;

  @ManyToOne(targetEntity = ClientOrder.class)
  @JoinColumn(name = "order_id", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private ClientOrder clientOrder;

  @ManyToOne(targetEntity = Dish.class)
  @JoinColumn(name = "dish_id", nullable = false)
  private Dish dish;


  @PrePersist
  public void calculateSubtotal() {
    this.subtotal = (double) (this.quantity * this.dish.getBasePrice());
  }
}
