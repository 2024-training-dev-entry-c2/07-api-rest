package restaurant_managment.Services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Repositories.OrderRepository;
import restaurant_managment.Utils.Handlers.FinalPriceHandler;
import restaurant_managment.Utils.Handlers.FrequentCustomerDiscountHandler;
import restaurant_managment.Utils.Handlers.PopularDishPriceAdjustmentHandler;
import restaurant_managment.Utils.Handlers.PriceHandler;
import restaurant_managment.interfaces.IOrderService;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;
  private final EntityManager entityManager;

  @Autowired
  public OrderService(OrderRepository orderRepository, EntityManager entityManager) {
    this.orderRepository = orderRepository;
    this.entityManager = entityManager;
  }

  public List<OrderModel> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Optional<OrderModel> getOrderById(Long id) {
    return orderRepository.findById(id);
  }

  @Transactional
  public OrderModel createOrder(OrderModel order) {
    Double totalPrice = calculateTotalPrice(order);
    order.setTotalPrice(totalPrice);
    OrderModel createdOrder = orderRepository.save(order);
    updateCustomerFrequency(createdOrder);
    updateDishPopularity(createdOrder);
    return createdOrder;
  }

  @Transactional
  public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
    return orderRepository.findById(id)
      .map(order -> {
        order.setReservation(updatedOrder.getReservation());
        order.setDishes(updatedOrder.getDishes());
        order.setStatus(updatedOrder.getStatus());
        Double totalPrice = calculateTotalPrice(order);
        order.setTotalPrice(totalPrice);
        OrderModel savedOrder = orderRepository.save(order);
        updateCustomerFrequency(savedOrder);
        updateDishPopularity(savedOrder);
        return savedOrder;
      })
      .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }

  public Double calculateTotalPrice(OrderModel order) {
    PriceHandler frequentCustomerHandler = new FrequentCustomerDiscountHandler();
    PriceHandler popularDishHandler = new PopularDishPriceAdjustmentHandler();
    PriceHandler finalHandler = new FinalPriceHandler();
    frequentCustomerHandler.setNextHandler(popularDishHandler);
    popularDishHandler.setNextHandler(finalHandler);
    return frequentCustomerHandler.handle(order);
  }

  private void updateCustomerFrequency(OrderModel order) {
    CustomerModel customer = order.getReservation().getCustomer();
    customer.updateFrecuency(entityManager);
  }

  private void updateDishPopularity(OrderModel order) {
    for (DishModel dish : order.getDishes()) {
      dish.updatePopularity(entityManager);
    }
  }
}