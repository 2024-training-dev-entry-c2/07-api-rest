package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Clientorder client;

    private LocalDate localDate;
    @ManyToMany
    @JoinTable(
            name = "order_dishfood",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dishfood_id")
    )
    private List<Dishfood> dishfoods;

    public Order(Clientorder client, LocalDate localDate, List<Dishfood> dishfoods) {
        this.client = client;
        this.localDate = localDate;
        this.dishfoods = dishfoods;
    }

    public Order() {
    }
}
