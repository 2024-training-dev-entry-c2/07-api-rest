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
import static org.mockito.Mockito.*;

class PopularDishPriceAdjustmentHandlerTest {

  @Test
  @DisplayName("Handle method applies price adjustment for popular dishes")
  void handleAppliesPriceAdjustmentForPopularDishes() {
    DishModel dish1 = new DishModel(1L, true, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    PopularDishPriceAdjustmentHandler priceAdjustmentHandler = new PopularDishPriceAdjustmentHandler();

    Double totalPrice = priceAdjustmentHandler.handle(order);

    assertEquals(30.0 + 0.573, totalPrice);
  }

  @Test
  @DisplayName("Handle method does not apply price adjustment for non-popular dishes")
  void handleDoesNotApplyPriceAdjustmentForNonPopularDishes() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    PopularDishPriceAdjustmentHandler priceAdjustmentHandler = new PopularDishPriceAdjustmentHandler();

    Double totalPrice = priceAdjustmentHandler.handle(order);

    assertEquals(30.0, totalPrice);
  }

  @Test
  @DisplayName("Handle method works with null nextHandler")
  void handleWorksWithNullNextHandler() {
    DishModel dish1 = new DishModel(1L, true, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    PopularDishPriceAdjustmentHandler priceAdjustmentHandler = new PopularDishPriceAdjustmentHandler();
    priceAdjustmentHandler.setNextHandler(null);

    Double totalPrice = priceAdjustmentHandler.handle(order);

    assertEquals(30.0 + 0.573, totalPrice);
  }

  @Test
  @DisplayName("Handle method works with nextHandler")
  void handleWorksWithNextHandler() {
    DishModel dish1 = new DishModel(1L, true, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    PopularDishPriceAdjustmentHandler priceAdjustmentHandler = new PopularDishPriceAdjustmentHandler();
    PriceHandler mockNextHandler = mock(PriceHandler.class);
    when(mockNextHandler.handle(any(OrderModel.class))).thenReturn(35.0);
    priceAdjustmentHandler.setNextHandler(mockNextHandler);

    Double totalPrice = priceAdjustmentHandler.handle(order);

    verify(mockNextHandler).handle(order);
    assertEquals(35.0, totalPrice);
  }
}