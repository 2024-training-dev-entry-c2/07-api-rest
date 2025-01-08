package com.example.restaurant.dto.menu;

import com.example.restaurant.dto.dish.DishResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<DishResponseDTO> dishes;

}
