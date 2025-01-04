package com.example.demo.controllers.DTO;

import com.example.demo.models.Dishfood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private String name;
    private List<Dishfood> dishfoods;

}
