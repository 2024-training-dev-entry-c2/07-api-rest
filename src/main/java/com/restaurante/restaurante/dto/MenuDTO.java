package com.restaurante.restaurante.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDTO {
    private Long id;
    private String name;
    private String description;
    private List<DishDTO> dishes;

}