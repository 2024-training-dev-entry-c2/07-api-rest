package com.restaurant.restaurant_management.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CLIENT")
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 45)
  private String name;

  @Column(nullable = false, length = 45)
  private String lastName;

  @Column(nullable = false, length = 45, unique = true)
  private String email;

  @Column(length = 15)
  private String phone;

  @Column(length = 200)
  private String address;

  @Column(nullable = false)
  private Boolean isFrequent = false;

  @OneToMany(targetEntity = ClientOrder.class,mappedBy = "client", cascade = CascadeType.REMOVE)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<ClientOrder> orders = new ArrayList<>();
}