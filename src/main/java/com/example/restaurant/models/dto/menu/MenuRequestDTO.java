package com.example.restaurant.models.dto.menu;

import lombok.Data;

import java.util.List;

@Data
public class MenuRequestDTO {
    private String name;
    private String description;
    private List<Long> dishIds;
}
