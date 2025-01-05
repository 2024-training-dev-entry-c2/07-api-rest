package com.example.demo.DTO;

import com.example.demo.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean isOften;
    private List<Order> orderList;
}
