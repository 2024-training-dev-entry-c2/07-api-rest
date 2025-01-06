package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DishfoodResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Boolean isPopular;
    private String menu;
    private List<OrderResponseDTO> orderList;
}
