package com.restaurant.restaurant_management.models;

import com.restaurant.restaurant_management.constants.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENT_ORDER")
public class ClientOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime orderDateTime;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(nullable = false)
  private Double total;

  @ManyToOne(targetEntity = Client.class)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(targetEntity = OrderDetail.class, mappedBy = "clientOrder", cascade = CascadeType.REMOVE)
  private List<OrderDetail> orderDetails = new ArrayList<>();

  public ClientOrder(Long id, LocalDateTime orderDateTime, OrderStatus status, Double total, Client client) {
    this.id = id;
    this.orderDateTime = orderDateTime;
    this.status = status;
    this.total = total;
    this.client = client;
  }
}
