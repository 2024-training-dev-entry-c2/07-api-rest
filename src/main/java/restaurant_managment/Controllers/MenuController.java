package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Proxy.MenuServiceProxy;
import restaurant_managment.Utils.Dto.Menu.MenuRequestDTO;
import restaurant_managment.Utils.Dto.Menu.MenuResponseDTO;
import restaurant_managment.Utils.Dto.Menu.MenuDTOConverter;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Services.MenuService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

  private MenuService menuService;
  private MenuServiceProxy menuServiceProxy;
  private MenuDTOConverter menuDTOConverter;

  @Autowired
  public MenuController(MenuService menuService, MenuServiceProxy menuServiceProxy, MenuDTOConverter menuDTOConverter) {
    this.menuService = menuService;
    this.menuServiceProxy = menuServiceProxy;
    this.menuDTOConverter = menuDTOConverter;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MenuResponseDTO createMenu(@RequestBody MenuRequestDTO menuRequestDTO) {
    MenuModel menuModel = MenuDTOConverter.toMenu(menuRequestDTO);
    MenuModel createdMenu = menuService.createMenu(menuModel, menuRequestDTO.getDishIds());
    if (createdMenu == null) {
      throw new IllegalStateException("Menu creation failed");
    }
    return MenuDTOConverter.toMenuResponseDTO(createdMenu);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long id) {
    Optional<MenuModel> menuModel = menuServiceProxy.getMenuById(id);
    return menuModel.map(menu -> ResponseEntity.ok(MenuDTOConverter.toMenuResponseDTO(menu)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
    List<MenuModel> menus = menuService.getAllMenus();
    List<MenuResponseDTO> responseDTOs = menus.stream()
      .map(MenuDTOConverter::toMenuResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDTO menuRequestDTO) {
    MenuModel updatedMenuModel = menuDTOConverter.toMenu(menuRequestDTO);
    MenuModel updatedMenu = menuService.updateMenu(id, updatedMenuModel, menuRequestDTO.getDishIds());
    MenuResponseDTO responseDTO = MenuDTOConverter.toMenuResponseDTO(updatedMenu);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
    menuService.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }
}