package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private boolean isOften;
    private List<Long> orderIds; // Contendrá datos más simplificados de las órdenes
}