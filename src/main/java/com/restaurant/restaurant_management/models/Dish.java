package com.restaurant.restaurant_management.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DISH")
public class Dish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 45)
  private String dishName;

  @Column(length = 350)
  private String description;

  @Column(nullable = false)
  private Integer basePrice;

  @Column(nullable = false)
  private Boolean isPopular = false;

  @ManyToOne(targetEntity = Menu.class)
  @JoinColumn(name = "menu_id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Menu menu;
}
