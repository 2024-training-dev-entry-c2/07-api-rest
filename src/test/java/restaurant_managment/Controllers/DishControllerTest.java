package restaurant_managment.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Proxy.DishServiceProxy;
import restaurant_managment.Services.DishService;
import restaurant_managment.Utils.Dto.Dish.DishDTOConverter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DishControllerTest {
  private final WebTestClient webTestClient;
  private final DishServiceProxy dishServiceProxy;
  private final DishService dishService;
  private final DishDTOConverter dishDTOConverter;

  public DishControllerTest() {
    dishServiceProxy = mock(DishServiceProxy.class);
    dishService = mock(DishService.class);
    dishDTOConverter = mock(DishDTOConverter.class);
    webTestClient = WebTestClient.bindToController(new DishController(dishService, dishServiceProxy, dishDTOConverter)).build();
  }

  @Test
  @DisplayName("Create dish")
  void createDish() {
    DishModel dish = new DishModel(1L, true, true, "DishTest1", 10.0, "test");
    when(dishService.saveDish(any(DishModel.class))).thenReturn(dish);

    webTestClient.post()
      .uri("/dishes")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dish)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishModel.class)
      .value(dish1 -> {
        assertEquals(dish.getName(), dish1.getName());
        assertEquals(dish.getPrice(), dish1.getPrice());
        assertEquals(dish.getId(), dish1.getId());
      });
    Mockito.verify(dishService).saveDish(any(DishModel.class));
  }

  @Test
  @DisplayName("Get dish by id")
  void getDishById() {
    DishModel dish = new DishModel(1L, true, true, "DishTest1", 10.0, "test");
    when(dishServiceProxy.getDishById(any(Long.class))).thenReturn(Optional.of(dish));

    webTestClient.get()
      .uri("/dishes/{id}", 1L)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishModel.class)
      .value(dish1 -> {
        assertEquals(dish.getName(), dish1.getName());
        assertEquals(dish.getPrice(), dish1.getPrice());
        assertEquals(dish.getId(), dish1.getId());
      });
    Mockito.verify(dishServiceProxy).getDishById(any(Long.class));
  }

  @Test
  @DisplayName("Get all dishes")
  void listDishes() {
    when(dishService.getAllDishes()).thenReturn(getAllDishes());

    webTestClient.get()
      .uri("/dishes")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(DishModel.class)
      .hasSize(3)
      .value(dishes -> {
        assertEquals(1L, dishes.get(0).getId());
        assertEquals(2L, dishes.get(1).getId());
        assertEquals(3L, dishes.get(2).getId());
      })
    ;
    Mockito.verify(dishService).getAllDishes();
  }

  @Test
  @DisplayName("Update dish")
  void updateDish() {
    DishModel dish = new DishModel(1L, true, true, "DishTest1", 10.0, "test");
    when(dishService.updateDish(anyLong(), any(DishModel.class))).thenReturn(dish);

    webTestClient.put()
      .uri("/dishes/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dish)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishModel.class)
      .value(dish1 -> {
        assertEquals(dish.getName(), dish1.getName());
        assertEquals(dish.getPrice(), dish1.getPrice());
        assertEquals(dish.getId(), dish1.getId());
      });
    Mockito.verify(dishService).updateDish(anyLong(), any(DishModel.class));
  }

  @Test
  @DisplayName("Delete dish")
  void deleteDish() {
    doNothing().when(dishService).deleteDish(anyLong());

    webTestClient.delete()
      .uri("/dishes/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent()
      .expectBody().isEmpty();
    Mockito.verify(dishService).deleteDish(anyLong());
  }

  private List<DishModel> getAllDishes() {
    return List.of(new DishModel(1L, true, true, "DishTest1", 10.0, "test"),
      new DishModel(2L, true, true, "DishTest2", 10.0, "test"),
      new DishModel(3L, true, true, "DishTest3", 10.0, "test"));
  }
}