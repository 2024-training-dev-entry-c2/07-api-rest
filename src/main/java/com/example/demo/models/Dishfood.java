package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.Order;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@Table(name = "dishfood")
public class Dishfood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;
    @Column(nullable = false)
    private Boolean isPopular = false;
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    @ManyToMany(mappedBy = "dishfoods")
    private List<Order> orderList;

    public Dishfood(String name, Double price, Menu menu) {
        this.name = name;
        this.price = price;
        this.menu = menu;
    }

    public Dishfood() {
    }
}
