package com.example.restaurant.models;

import jakarta.persistence.CascadeType;
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

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long menuId;

  @Column(nullable = false)
  private String name;

  private String description;

  @OneToMany(
          targetEntity = Dish.class,
          fetch = FetchType.EAGER,
          mappedBy = "menu",
          cascade = CascadeType.ALL
  )
  private List<Dish> dishes;

  public Menu(Long menuId, String name, String description) {
    this.menuId = menuId;
    this.name = name;
    this.description = description;
  }
}
