package com.example.restaurant.dto.client;

import com.example.restaurant.constants.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String clientType;

}
