package restaurant_managment.Observer;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import restaurant_managment.Models.DishModel;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DishObserverTest {

  private DishObserver dishObserver;
  private EntityManager mockEntityManager;
  private DishModel mockDish1;
  private DishModel mockDish2;
  private List<DishModel> dishes;

  @BeforeEach
  public void setUp() {
    mockEntityManager = mock(EntityManager.class);
    mockDish1 = mock(DishModel.class);
    mockDish2 = mock(DishModel.class);
    dishes = Arrays.asList(mockDish1, mockDish2);
    dishObserver = new DishObserver(dishes, mockEntityManager);
  }

  @Test
  public void testUpdate_DishIsPopular() {

    when(mockDish1.getIsPopular()).thenReturn(true);
    when(mockDish2.getIsPopular()).thenReturn(false);

    dishObserver.update();

    verify(mockDish1).updatePopularity(mockEntityManager);
    verify(mockDish2).updatePopularity(mockEntityManager);
    verify(mockDish1).getIsPopular();
    verify(mockDish2).getIsPopular();
    verify(mockDish1).getName();
    verify(mockDish2, never()).getName();
    System.out.println("El plato " + mockDish1.getName() + " es popular!!");
  }

  @Test
  public void testUpdate_DishIsNotPopular() {

    when(mockDish1.getIsPopular()).thenReturn(false);
    when(mockDish2.getIsPopular()).thenReturn(false);

    dishObserver.update();

    verify(mockDish1).updatePopularity(mockEntityManager);
    verify(mockDish2).updatePopularity(mockEntityManager);
    verify(mockDish1).getIsPopular();
    verify(mockDish2).getIsPopular();
    verify(mockDish1, never()).getName();
    verify(mockDish2, never()).getName();
  }
}