package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Proxy.OrderServiceProxy;
import restaurant_managment.Utils.Dto.Order.OrderRequestDTO;
import restaurant_managment.Utils.Dto.Order.OrderResponseDTO;
import restaurant_managment.Utils.Dto.Order.OrderDTOConverter;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Services.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderServiceProxy orderServiceProxy;

  @Autowired
  private OrderDTOConverter orderDTOConverter;

  @PostMapping
  public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
    OrderModel orderModel = orderDTOConverter.toOrder(orderRequestDTO);
    OrderModel createdOrder = orderService.createOrder(orderModel);
    OrderResponseDTO responseDTO = orderDTOConverter.toOrderResponseDTO(createdOrder);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
    Optional<OrderModel> orderModel = orderServiceProxy.getOrderById(id);
    return orderModel.map(order -> ResponseEntity.ok(orderDTOConverter.toOrderResponseDTO(order)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
    List<OrderModel> orders = orderService.getAllOrders();
    List<OrderResponseDTO> responseDTOs = orders.stream()
      .map(orderDTOConverter::toOrderResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
    OrderModel updatedOrderModel = orderDTOConverter.toOrder(orderRequestDTO);
    OrderModel updatedOrder = orderService.updateOrder(id, updatedOrderModel);
    OrderResponseDTO responseDTO = orderDTOConverter.toOrderResponseDTO(updatedOrder);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}