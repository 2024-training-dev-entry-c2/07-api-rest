package com.restaurante.restaurante.dto;
import com.restaurante.restaurante.models.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderDate;
    private Integer totalPrice;
    private Long clientId;
    private ClientDTO clientDTO;
    private List<DishDTO> dishes;
}