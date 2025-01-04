package com.example.demo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.Order;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "client")
public class Clientorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private Boolean isOften;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orderList;

    public Clientorder(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Clientorder() {
    }
}
