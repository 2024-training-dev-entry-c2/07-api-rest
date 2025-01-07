package com.training.restaurant.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

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
@Table(name="menus")
@Getter
@Setter
@NoArgsConstructor
public class Menu implements Serializable {

    private final static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String schedule;
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dishes> dishes;

    public Menu(String name, String schedule, ZonedDateTime createdAt) {
        this.name = name;
        this.schedule = schedule;
        this.createdAt = createdAt;
    }
}
