package com.example.demo.controllers.DTO;

import com.example.demo.models.Clientorder;
import com.example.demo.models.Dishfood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private Clientorder client;
    private LocalDate localDate;
    private List<Dishfood> dishfoods;


}
