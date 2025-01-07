package com.training.restaurant.models;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dishes")
@Getter
@Setter
@NoArgsConstructor
public class Dishes implements Serializable{

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private DishType type;
    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDish> orderDishes = new ArrayList<>();

    public Dishes(String name, String type, Double price) {
        this.name = name;
        this.type = DishType.fromString(type);
        this.price = price;
    }

    public Dishes(Long id, String name, String type, Double price) {
        this.id = id;
        this.name = name;
        this.type = DishType.fromString(type);
        this.price = price;
    }
}
