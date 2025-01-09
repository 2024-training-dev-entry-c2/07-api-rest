package restaurant_managment.Proxy;

import restaurant_managment.Models.DishModel;
import restaurant_managment.interfaces.IDishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DishServiceProxyTest {

  @Mock
  private IDishService dishService;

  @InjectMocks
  private DishServiceProxy dishServiceProxy;

  private DishModel dish;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    dish = new DishModel(1L, true, true, "Pizza", 15.0, "Delicious pizza");
  }

  @Test
  void testGetDishById_DishNotInCache() {
    when(dishService.getDishById(1L)).thenReturn(Optional.of(dish));

    Optional<DishModel> result = dishServiceProxy.getDishById(1L);

    assertTrue(result.isPresent());
    assertEquals(dish, result.get());

    verify(dishService, times(1)).getDishById(1L);
  }

  @Test
  void testGetDishById_DishInCache() {
    when(dishService.getDishById(1L)).thenReturn(Optional.of(dish));

    dishServiceProxy.getDishById(1L);

    Optional<DishModel> result = dishServiceProxy.getDishById(1L);

    assertTrue(result.isPresent());
    assertEquals(dish, result.get());

    verify(dishService, times(1)).getDishById(1L);
  }

}
