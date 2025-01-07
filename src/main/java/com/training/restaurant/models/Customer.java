package com.training.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerType type;
    @Column
    private Integer age;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private Boolean isActive;
    @Column
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orders> orders;

    public Customer(String name, String email, String clientType, Integer age, String phone, String address, ZonedDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.type = CustomerType.fromString(clientType);
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.isActive = true;
        this.createdAt = createdAt;
    }
}
