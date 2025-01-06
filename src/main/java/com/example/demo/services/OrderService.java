package com.example.demo.services;

import com.example.demo.DTO.OrderRequestDTO;
import com.example.demo.DTO.OrderResponseDTO;
import com.example.demo.DTO.converterDTO.OrderConverter;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.observer.FrequentClientObserver;
import com.example.demo.observer.PopularDishObserver;
import com.example.demo.observer.Subject;
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

    private final Subject subject;


    public OrderService(OrderRepository repository, ClientRepository clientRepository, DishfoodRepository dishfoodRepository, FrequentClientObserver frequentClientObserver,
                        PopularDishObserver popularDishObserver) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.dishfoodRepository = dishfoodRepository;
        this.subject = new Subject();
        this.subject.addObserver(frequentClientObserver);
        this.subject.addObserver(popularDishObserver);
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderDTO) {
        Client client = clientRepository.findById(orderDTO.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));
        List<Dishfood> dishfoods = dishfoodRepository.findAllById(orderDTO.getDishfoodIds());
        if (dishfoods.isEmpty())  throw new RuntimeException("Dishfoods not found");
        Order order = OrderConverter.toEntity(orderDTO, client, dishfoods);
        checkClient(client);
        checkDishFood(dishfoods);
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

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("menu not found"));
        Order orderUpdated = Order.builder()
                .id(existingOrder.getId())
                .localDate(orderRequestDTO.getLocalDate())
                .client(clientRepository.getReferenceById(orderRequestDTO.getClientId()))
                .dishfoods(dishfoodRepository.findAllById(orderRequestDTO.getDishfoodIds()))
                .build();
        repository.save(orderUpdated);
        return OrderConverter.toResponseDTO(orderUpdated);

    }

    public void removeOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        repository.deleteById(id);
    }

    private void checkClient(Client client) {
        long orderCount = repository.countByClient_Id(client.getId());
        if (orderCount >= 10 && !client.getIsOften()) {
            client.setIsOften(true);
            clientRepository.save(client);
            subject.notifyObservers("El cliente " + client.getName() + " ahora es un cliente frecuente.");
        }
    }
    private void  checkDishFood(List<Dishfood> dishfoods){
        for (Dishfood dish : dishfoods) {
            long dishCount = repository.countByDishfoods_Id(dish.getId());
            if (dishCount > 100 && !dish.getIsPopular()) {
                dish.setIsPopular(true);
                dishfoodRepository.save(dish);
                subject.notifyObservers("El plato " + dish.getName() + " ahora es popular.");
            }
        }
    }


}
