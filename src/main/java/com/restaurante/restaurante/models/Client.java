package com.restaurante.restaurante.models;

import com.restaurante.restaurante.utils.UserType;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Orders> orders;

    private String userType = String.valueOf(UserType.REGULAR);
    private Integer totalOrders = 0;

    public Client(Long id, String name, String email, List<Orders> orders, String userType, Integer totalOrders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.orders = orders;
        this.totalOrders = totalOrders;
        this.userType = userType;
    }

    public Client() {
    }
}