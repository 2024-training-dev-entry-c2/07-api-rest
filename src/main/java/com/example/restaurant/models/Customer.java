package com.example.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @OneToMany(
            targetEntity = Order.class,
            fetch = FetchType.LAZY,
            mappedBy = "customer"
    )
    private List<Order> orders;

    public Customer(String name, String lastName, String email, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.orders = new ArrayList<>();
    }

    public Customer(Long id, String name, String lastName, String email, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.orders = new ArrayList<>();
    }
}
