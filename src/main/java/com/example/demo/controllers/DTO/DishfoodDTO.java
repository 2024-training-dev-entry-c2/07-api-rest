package com.example.demo.controllers.DTO;

import com.example.demo.models.Menu;
import com.example.demo.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DishfoodDTO {
    private Long id;
    private String name;
    private Double price;
    private Boolean isPopular = false;
    private Menu menu;
    private List<Order> orderList;
}
