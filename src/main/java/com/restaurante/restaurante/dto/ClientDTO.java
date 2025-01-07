package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String userType;
    private Integer totalOrders;
    private List<OrderDTO> orders;
}