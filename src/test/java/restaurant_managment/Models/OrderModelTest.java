package restaurant_managment.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderModelTest {

  private OrderModel order;
  private DishModel mockDish1;
  private DishModel mockDish2;

  @BeforeEach
  public void setUp() {
    mockDish1 = mock(DishModel.class);
    mockDish2 = mock(DishModel.class);

    when(mockDish1.getPrice()).thenReturn(10.0);
    when(mockDish2.getPrice()).thenReturn(15.0);

    List<DishModel> dishes = Arrays.asList(mockDish1, mockDish2);
    order = new OrderModel();
    order.setDishes(dishes);
  }

  @Test
  public void testGetTotalPrice_CalculatesTotalWhenNull() {

    Double totalPrice = order.getTotalPrice();

    assertEquals(25.0, totalPrice);
  }

  @Test
  public void testGetTotalPrice_ReturnsExistingTotal() {

    order.setTotalPrice(30.0);

    Double totalPrice = order.getTotalPrice();

    assertEquals(30.0, totalPrice);
  }

  @Test
  public void testGetTotalPrice_EmptyDishesList() {

    order.setDishes(Arrays.asList());
    order.setTotalPrice(null);

    Double totalPrice = order.getTotalPrice();

    assertEquals(0.0, totalPrice);
  }

  @Test
  public void testGetTotalPrice_NullDishesList() {

    order.setDishes(null);
    order.setTotalPrice(null);

    Double totalPrice = order.getTotalPrice();

    assertEquals(0.0, totalPrice);
  }
}