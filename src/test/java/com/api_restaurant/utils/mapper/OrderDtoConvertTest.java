package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.order.OrderRequestDTO;
import com.api_restaurant.dto.order.OrderResponseDTO;
import com.api_restaurant.models.Client;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Order;
import com.api_restaurant.repositories.ClientRepository;
import com.api_restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderDtoConvertTest {

    private OrderDtoConvert orderDtoConvert;
    private ClientRepository clientRepository;
    private DishRepository dishRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        dishRepository = mock(DishRepository.class);
        orderDtoConvert = new OrderDtoConvert(clientRepository, dishRepository);
    }

    @Test
    @DisplayName("Convert to Response DTO - Success")
    void convertToResponseDto() {
        Client client = new Client();
        client.setId(1L);

        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Dish 1");

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Dish 2");

        Order order = new Order();
        order.setId(1L);
        order.setClient(client);
        order.setDishes(List.of(dish1, dish2));
        order.setTotal(25.0);

        OrderResponseDTO responseDTO = orderDtoConvert.convertToResponseDto(order);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals(1L, responseDTO.getClientId());
        assertNotNull(responseDTO.getDishes());
        assertEquals(2, responseDTO.getDishes().size());
        assertEquals(25.0, responseDTO.getTotal());
    }

    @Test
    @DisplayName("Convert to Entity - Success")
    void convertToEntity() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setDishIds(List.of(1L, 2L));

        Client client = new Client();
        client.setId(1L);

        Dish dish1 = new Dish();
        dish1.setId(1L);

        Dish dish2 = new Dish();
        dish2.setId(2L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dish2));

        Order order = orderDtoConvert.convertToEntity(requestDTO);

        assertNotNull(order);
        assertEquals(client, order.getClient());
        assertNotNull(order.getDishes());
        assertEquals(2, order.getDishes().size());
    }

    @Test
    @DisplayName("Convert to Entity - Client Not Found")
    void convertToEntityClientNotFound() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setDishIds(List.of(1L, 2L));

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Order order = orderDtoConvert.convertToEntity(requestDTO);

        assertNull(order.getClient());
        assertNotNull(order.getDishes());
        assertEquals(2, order.getDishes().size());
    }

    @Test
    @DisplayName("Convert to Entity - Dish Not Found")
    void convertToEntityDishNotFound() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setDishIds(List.of(1L, 2L));

        Client client = new Client();
        client.setId(1L);

        Dish dish1 = new Dish();
        dish1.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.empty());

        Order order = orderDtoConvert.convertToEntity(requestDTO);

        assertNotNull(order);
        assertEquals(client, order.getClient());
        assertNotNull(order.getDishes());
        assertEquals(2, order.getDishes().size());
        assertNull(order.getDishes().get(1));
    }

    @Test
    @DisplayName("Convert to Entity - Some Dishes Not Found")
    void convertToEntitySomeDishesNotFound() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setDishIds(List.of(1L, 2L, 3L));

        Client client = new Client();
        client.setId(1L);

        Dish dish1 = new Dish();
        dish1.setId(1L);

        Dish dish2 = new Dish();
        dish2.setId(2L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dish2));
        when(dishRepository.findById(3L)).thenReturn(Optional.empty());

        Order order = orderDtoConvert.convertToEntity(requestDTO);

        assertNotNull(order);
        assertEquals(client, order.getClient());
        assertNotNull(order.getDishes());
        assertEquals(3, order.getDishes().size());
        assertNull(order.getDishes().get(2));
    }
}