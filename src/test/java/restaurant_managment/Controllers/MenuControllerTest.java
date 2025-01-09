package restaurant_managment.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Proxy.MenuServiceProxy;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Services.MenuService;
import restaurant_managment.Utils.Dto.Menu.MenuDTOConverter;
import restaurant_managment.Utils.Dto.Menu.MenuRequestDTO;
import restaurant_managment.Utils.Dto.Menu.MenuResponseDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuControllerTest {

  private final WebTestClient webTestClient;
  private final MenuServiceProxy menuServiceProxy;
  private final MenuService menuService;
  private final MenuDTOConverter menuDTOConverter;
  private final DishRepository dishRepository;

  public MenuControllerTest() {
    dishRepository = mock(DishRepository.class);
    menuDTOConverter = new MenuDTOConverter(dishRepository);
    menuServiceProxy = mock(MenuServiceProxy.class);
    menuService = mock(MenuService.class);

    webTestClient = WebTestClient.bindToController(new MenuController(menuService, menuServiceProxy, menuDTOConverter)).build();
  }

  @Test
  @DisplayName("Create menu with DTOs")
  void createMenu() {

    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    DishModel dish2 = new DishModel(6L, false, true, "Grilled Chicken Caesar Salad", 11.49, "Crispy romaine lettuce, grilled chicken, Caesar dressing, and croutons.");
    DishModel dish3 = new DishModel(7L, true, true, "Vegetable Stir Fry", 10.29, "Mixed vegetables stir-fried in soy sauce and served over steamed rice.");
    List<DishModel> dishes = List.of(dish1, dish2, dish3);

    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    MenuModel menu = new MenuModel(9L, "Another Menu", dishes);
    MenuResponseDTO menuResponseDTO = MenuDTOConverter.toMenuResponseDTO(menu);

    when(menuService.createMenu(any(MenuModel.class), eq(List.of(dish1.getId(), dish2.getId(), dish3.getId())))).thenReturn(menu);

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO("Another Menu", List.of(dish1.getId(), dish2.getId(), dish3.getId()));

    webTestClient.post()
      .uri("/menu")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(response -> {
        assertEquals(menuResponseDTO.getName(), response.getName());
        assertEquals(menuResponseDTO.getId(), response.getId());
        assertEquals(3, response.getDishes().size());
        assertEquals(menuResponseDTO.getDishes().get(0).getName(), response.getDishes().get(0).getName());
        assertEquals(menuResponseDTO.getDishes().get(1).getName(), response.getDishes().get(1).getName());
        assertEquals(menuResponseDTO.getDishes().get(2).getName(), response.getDishes().get(2).getName());
      });

    Mockito.verify(menuService).createMenu(any(MenuModel.class), anyList());
    Mockito.verify(dishRepository).findAllById(anyList());
  }


  @Test
  @DisplayName("Create menu - failure")
  void createMenuFailure() {
    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    DishModel dish2 = new DishModel(6L, false, true, "Grilled Chicken Caesar Salad", 11.49, "Crispy romaine lettuce, grilled chicken, Caesar dressing, and croutons.");
    DishModel dish3 = new DishModel(7L, true, true, "Vegetable Stir Fry", 10.29, "Mixed vegetables stir-fried in soy sauce and served over steamed rice.");

    List<DishModel> dishes = List.of(dish1, dish2, dish3);

    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    when(menuService.createMenu(any(MenuModel.class), eq(List.of(dish1.getId(), dish2.getId(), dish3.getId())))).thenReturn(null);

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO("Another Menu", List.of(dish1.getId(), dish2.getId(), dish3.getId()));

    webTestClient.post()
      .uri("/menu")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().is5xxServerError();

    Mockito.verify(menuService).createMenu(any(MenuModel.class), anyList());
    Mockito.verify(dishRepository).findAllById(anyList());
  }

  @Test
  @DisplayName("Get menu by ID")
  void getMenuById() {
    DishModel dish1 = new DishModel(5L, null, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);
    MenuModel menu = new MenuModel(9L, "Another Menu", dishes);

    when(menuServiceProxy.getMenuById(9L)).thenReturn(Optional.of(menu));

    webTestClient.get()
      .uri("/menu/9")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menuResponse -> {
        assertEquals(menu.getName(), menuResponse.getName());
        assertEquals(menu.getId(), menuResponse.getId());
        assertEquals(1, menuResponse.getDishes().size());
        assertEquals(dish1.getName(), menuResponse.getDishes().get(0).getName());
      });

    Mockito.verify(menuServiceProxy).getMenuById(9L);
  }

  @Test
  @DisplayName("Get all menus")
  void getAllMenus() {
    DishModel dish1 = new DishModel(5L, null, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);
    MenuModel menu1 = new MenuModel(9L, "Another Menu", dishes);
    MenuModel menu2 = new MenuModel(10L, "Another Menu 2", dishes);
    List<MenuModel> menus = List.of(menu1, menu2);

    when(menuService.getAllMenus()).thenReturn(menus);

    webTestClient.get()
      .uri("/menu")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(MenuResponseDTO.class)
      .value(menuResponses -> {
        assertEquals(2, menuResponses.size());
        assertEquals(menu1.getName(), menuResponses.get(0).getName());
        assertEquals(menu2.getName(), menuResponses.get(1).getName());
      });

    Mockito.verify(menuService).getAllMenus();
  }

  @Test
  @DisplayName("Update menu")
  void updateMenu() {
    DishModel dish1 = new DishModel(5L, null, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);

    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    MenuModel updatedMenu = new MenuModel(9L, "Updated Menu", dishes);
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO("Updated Menu", List.of(dish1.getId()));

    when(menuService.updateMenu(eq(9L), any(MenuModel.class), anyList())).thenReturn(updatedMenu);

    webTestClient.put()
      .uri("/menu/9")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menuResponse -> {
        assertEquals(updatedMenu.getName(), menuResponse.getName());
        assertEquals(updatedMenu.getId(), menuResponse.getId());
        assertEquals(1, menuResponse.getDishes().size());
        assertEquals(dish1.getName(), menuResponse.getDishes().get(0).getName());
      });

    Mockito.verify(menuService).updateMenu(eq(9L), any(MenuModel.class), anyList());
    Mockito.verify(dishRepository).findAllById(anyList());
  }

  @Test
  @DisplayName("Delete menu")
  void deleteMenu() {
    webTestClient.delete()
      .uri("/menu/9")
      .exchange()
      .expectStatus().isNoContent();

    Mockito.verify(menuService).deleteMenu(9L);
  }
}