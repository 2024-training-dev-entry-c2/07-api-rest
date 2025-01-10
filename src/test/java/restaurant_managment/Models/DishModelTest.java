package restaurant_managment.Models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DishModelTest {

  private DishModel dish;
  private EntityManager mockEntityManager;
  private TypedQuery<Long> mockQuery;

  @BeforeEach
  public void setUp() {
    dish = new DishModel();
    dish.setId(1L);
    dish.setIsPopular(false);
    dish.setIsAvailable(true);
    dish.setName("Test Dish");
    dish.setPrice(10.0);
    dish.setDescription("Test Description");

    mockEntityManager = mock(EntityManager.class);
    mockQuery = mock(TypedQuery.class);
  }

  @Test
  public void testUpdatePopularity_NotPopular() {

    when(mockEntityManager.createQuery("SELECT COUNT(o) FROM OrderModel o JOIN o.dishes d WHERE d.id = :dishId", Long.class))
      .thenReturn(mockQuery);
    when(mockQuery.setParameter("dishId", dish.getId())).thenReturn(mockQuery);
    when(mockQuery.getSingleResult()).thenReturn(10L);

    dish.updatePopularity(mockEntityManager);

    assertFalse(dish.getIsPopular());
  }

  @Test
  public void testUpdatePopularity_AlreadyPopular() {

    dish.setIsPopular(true);

    dish.updatePopularity(mockEntityManager);

    verify(mockEntityManager, never()).createQuery(anyString(), eq(Long.class));
  }

  @Test
  public void testVerifyTotalOrders_Popular() {

    dish.verifyTotalOrders(100);

    assertTrue(dish.getIsPopular());
  }

  @Test
  public void testVerifyTotalOrders_NotPopular() {

    dish.verifyTotalOrders(5);

    assertFalse(dish.getIsPopular());
  }
}