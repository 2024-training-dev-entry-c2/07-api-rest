package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Models.DishModel;
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

  @Autowired
  private MenuService menuService;

  @Autowired
  private MenuDTOConverter menuDTOConverter;

  @PostMapping
  public ResponseEntity<MenuResponseDTO> createMenu(@RequestBody MenuRequestDTO menuRequestDTO) {
    MenuModel menuModel = menuDTOConverter.toMenu(menuRequestDTO);
    MenuModel createdMenu = menuService.createMenu(menuModel, menuRequestDTO.getDishIds());
    MenuResponseDTO responseDTO = menuDTOConverter.toMenuResponseDTO(createdMenu);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long id) {
    Optional<MenuModel> menuModel = menuService.getMenuById(id);
    return menuModel.map(menu -> ResponseEntity.ok(menuDTOConverter.toMenuResponseDTO(menu)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
    List<MenuModel> menus = menuService.getAllMenus();
    List<MenuResponseDTO> responseDTOs = menus.stream()
      .map(menuDTOConverter::toMenuResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDTO menuRequestDTO) {
    MenuModel updatedMenuModel = menuDTOConverter.toMenu(menuRequestDTO);
    MenuModel updatedMenu = menuService.updateMenu(id, updatedMenuModel, menuRequestDTO.getDishIds());
    MenuResponseDTO responseDTO = menuDTOConverter.toMenuResponseDTO(updatedMenu);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
    menuService.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }
}