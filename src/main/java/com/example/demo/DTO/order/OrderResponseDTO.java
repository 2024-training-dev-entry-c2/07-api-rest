package com.example.demo.DTO.order;

import com.example.demo.DTO.client.ClientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private ClientResponseDTO client;
    private LocalDate localDate;
    private List<Long> dishfoodIds;
    private double totalPrice ;

}
