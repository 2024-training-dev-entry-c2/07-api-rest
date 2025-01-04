package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Handlers.DiscountHandler;
import restaurant_managment.Handlers.FrequentCustomerHandler;
import restaurant_managment.Handlers.PopularDishHandler;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Observer.IObservable;
import restaurant_managment.Observer.IObserver;
import restaurant_managment.Repositories.OrderRepository;
import restaurant_managment.interfaces.IOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService, IObservable {

  private final OrderRepository orderRepository;
  private final DiscountHandler discountHandler;
  private FrequentCustomerHandler frequentCustomerHandler;
  private PopularDishHandler popularDishHandler;
  private List<IObserver> observers = new ArrayList<>();

  @Autowired
  public OrderService(OrderRepository orderRepository, DiscountHandler discountHandler, FrequentCustomerHandler frequentCustomerHandler, PopularDishHandler popularDishHandler) {
    this.orderRepository = orderRepository;

    // Configurar la cadena de manejadores
    discountHandler.setNextHandler(frequentCustomerHandler);
    frequentCustomerHandler.setNextHandler(popularDishHandler);
    this.discountHandler = discountHandler;
  }

  @Override
  public List<OrderModel> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Optional<OrderModel> getOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public OrderModel createOrder(OrderModel order) {
    // Procesar el pedido a través de la cadena de manejadores
    discountHandler.handle(order);

    // Guardar el pedido procesado
    return orderRepository.save(order);
  }

  public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
    return orderRepository.findById(id)
      .map(order -> {
        order.setReservation(updatedOrder.getReservation());
        order.setDishes(updatedOrder.getDishes());
        order.setStatus(updatedOrder.getStatus());
        return orderRepository.save(order);
      })
      .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }

  @Override
  public void addObserver(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(IObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(String message) {
    for (IObserver observer : observers) {
      observer.update(message);
    }
  }
}