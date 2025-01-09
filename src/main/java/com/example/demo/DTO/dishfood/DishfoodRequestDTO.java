package com.example.demo.DTO.dishfood;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishfoodRequestDTO {
    private String name;
    private Double price;
    private Boolean isPopular;
    private Long menuId;
}
