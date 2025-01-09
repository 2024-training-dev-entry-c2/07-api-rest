package com.api_restaurant.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Boolean frequent;
}
