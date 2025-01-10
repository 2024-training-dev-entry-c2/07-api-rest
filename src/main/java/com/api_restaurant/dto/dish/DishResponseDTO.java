package com.api_restaurant.dto.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean specialDish;
    private MenuSummaryDTO menu;

    @Getter
    @Setter
    public static class MenuSummaryDTO {
        private Long id;
        private String name;
    }
}