package restaurant_managment.Utils.Dto.Menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuDTOConverterTest {

  private DishRepository dishRepository;
  private MenuDTOConverter menuDTOConverter;

  @BeforeEach
  void setUp() {
    dishRepository = mock(DishRepository.class);
    menuDTOConverter = new MenuDTOConverter(dishRepository);
  }

  @Test
  @DisplayName("Convert MenuRequestDTO to MenuModel successfully")
  void toMenuSuccess() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Test Menu");
    menuRequestDTO.setDishIds(List.of(1L, 2L));

    MenuModel menuModel = MenuDTOConverter.toMenu(menuRequestDTO);

    assertNotNull(menuModel);
    assertEquals("Test Menu", menuModel.getName());
    assertEquals(2, menuModel.getDishes().size());

    verify(dishRepository).findAllById(anyList());
  }

  @Test
  @DisplayName("Convert MenuRequestDTO to MenuModel with missing dishes")
  void toMenuWithMissingDishes() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    List<DishModel> dishes = List.of(dish1);
    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Test Menu");
    menuRequestDTO.setDishIds(List.of(1L, 2L));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      MenuDTOConverter.toMenu(menuRequestDTO);
    });

    assertEquals("One or more dishes not found", exception.getMessage());

    verify(dishRepository).findAllById(anyList());
  }

  @Test
  @DisplayName("Convert MenuModel to MenuResponseDTO successfully")
  void toMenuResponseDTOSuccess() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);

    MenuModel menuModel = new MenuModel(1L, "Test Menu", dishes);

    MenuResponseDTO menuResponseDTO = MenuDTOConverter.toMenuResponseDTO(menuModel);

    assertNotNull(menuResponseDTO);
    assertEquals(1L, menuResponseDTO.getId());
    assertEquals("Test Menu", menuResponseDTO.getName());
    assertEquals(2, menuResponseDTO.getDishes().size());

    DishResponseDTO dishResponse1 = menuResponseDTO.getDishes().get(0);
    assertEquals(1L, dishResponse1.getId());
    assertEquals("Dish 1", dishResponse1.getName());
    assertEquals("Description 1", dishResponse1.getDescription());
    assertEquals(10.0, dishResponse1.getPrice());

    DishResponseDTO dishResponse2 = menuResponseDTO.getDishes().get(1);
    assertEquals(2L, dishResponse2.getId());
    assertEquals("Dish 2", dishResponse2.getName());
    assertEquals("Description 2", dishResponse2.getDescription());
    assertEquals(20.0, dishResponse2.getPrice());
  }
}