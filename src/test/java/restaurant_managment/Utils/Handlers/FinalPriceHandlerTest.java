package restaurant_managment.Utils.Handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.ReservationModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FinalPriceHandlerTest {

  @Test
  @DisplayName("Handle method returns correct total price")
  void handleReturnsTotalPrice() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    FinalPriceHandler finalPriceHandler = new FinalPriceHandler();

    Double totalPrice = finalPriceHandler.handle(order);

    assertEquals(30.0, totalPrice);
  }

  @Test
  @DisplayName("Handle method works with null nextHandler")
  void handleWorksWithNullNextHandler() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    FinalPriceHandler finalPriceHandler = new FinalPriceHandler();
    finalPriceHandler.setNextHandler(null);

    Double totalPrice = finalPriceHandler.handle(order);

    assertEquals(30.0, totalPrice);
  }

  @Test
  @DisplayName("Handle method works with nextHandler")
  void handleWorksWithNextHandler() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    FinalPriceHandler finalPriceHandler = new FinalPriceHandler();
    PriceHandler mockNextHandler = mock(PriceHandler.class);
    when(mockNextHandler.handle(any(OrderModel.class))).thenReturn(35.0);
    finalPriceHandler.setNextHandler(mockNextHandler);

    Double totalPrice = finalPriceHandler.handle(order);

    verify(mockNextHandler).handle(order);
    assertEquals(35.0, totalPrice);
  }
}