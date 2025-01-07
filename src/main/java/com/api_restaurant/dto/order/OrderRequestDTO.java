package com.api_restaurant.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Long clientId;
    private List<Long> dishIds;
}