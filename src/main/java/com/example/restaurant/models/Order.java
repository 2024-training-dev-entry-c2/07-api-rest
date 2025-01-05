package com.example.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_table")
public class Order {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private Float total;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToMany(targetEntity = Dish.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
          name = "order_dish",
          joinColumns = @JoinColumn(name = "order_id"),
          inverseJoinColumns = @JoinColumn(name = "dish_id")
  )
  private List<Dish> dishes;

}
