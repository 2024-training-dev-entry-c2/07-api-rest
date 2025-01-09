package com.example.demo.DTO.order;

import com.example.demo.DTO.client.ClientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private ClientResponseDTO client;
    private LocalDate localDate;
    private List<Long> dishfoodIds;
    private double totalPrice = 0.0;

}
