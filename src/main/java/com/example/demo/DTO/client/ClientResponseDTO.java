package com.example.demo.DTO.client;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean isOften=false;
    private List<Long> orderIds;
}