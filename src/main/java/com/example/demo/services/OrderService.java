package com.example.demo.services;

import com.example.demo.DTO.OrderRequestDTO;
import com.example.demo.DTO.OrderResponseDTO;
import com.example.demo.DTO.converterDTO.OrderConverter;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.observer.ClientObserver;
import com.example.demo.observer.DishFoodObserver;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository repository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final DishfoodRepository dishfoodRepository;


    public OrderService(OrderRepository repository, ClientRepository clientRepository, DishfoodRepository dishfoodRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.dishfoodRepository = dishfoodRepository;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderDTO) {
        Client client = clientRepository.findById(orderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        List<Dishfood> dishfoods = dishfoodRepository.findAllById(orderDTO.getDishfoodIds());
        if (dishfoods.isEmpty()) {
            throw new RuntimeException("Dishfoods not found");
        }
        Order order = OrderConverter.toEntity(orderDTO, client, dishfoods);
        ClientObserver clientObserver = new ClientObserver(client, repository);
        clientObserver.update();
        for (Dishfood dishfood : order.getDishfoods()) {
            DishFoodObserver dishFoodObserver = new DishFoodObserver(dishfood, repository);
            dishFoodObserver.update();
        }
        return OrderConverter.toResponseDTO(repository.save(order));

    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderConverter.toResponseDTO(order);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return repository.findAll().stream()
                .map(OrderConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void removeOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        repository.deleteById(id);
    }
}
