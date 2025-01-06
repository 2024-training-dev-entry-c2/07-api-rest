package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Utils.Dto.Dish.DishRequestDTO;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;
import restaurant_managment.Utils.Dto.Dish.DishDTOConverter;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Services.DishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dishes")
public class DishController {

  @Autowired
  private DishService dishService;

  @Autowired
  private DishDTOConverter dishDTOConverter;

  @PostMapping
  public ResponseEntity<DishResponseDTO> createDish(@RequestBody DishRequestDTO dishRequestDTO) {
    DishModel dishModel = dishDTOConverter.toDish(dishRequestDTO);
    DishModel createdDish = dishService.saveDish(dishModel);
    DishResponseDTO responseDTO = dishDTOConverter.toDishResponseDTO(createdDish);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DishResponseDTO> getDishById(@PathVariable Long id) {
    Optional<DishModel> dishModel = dishService.getDishById(id);
    return dishModel.map(dish -> ResponseEntity.ok(dishDTOConverter.toDishResponseDTO(dish)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<DishResponseDTO>> getAllDishes() {
    List<DishModel> dishes = dishService.getAllDishes();
    List<DishResponseDTO> responseDTOs = dishes.stream()
      .map(dishDTOConverter::toDishResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO) {
    DishModel updatedDishModel = dishDTOConverter.toDish(dishRequestDTO);
    DishModel updatedDish = dishService.updateDish(id, updatedDishModel);
    DishResponseDTO responseDTO = dishDTOConverter.toDishResponseDTO(updatedDish);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
    dishService.deleteDish(id);
    return ResponseEntity.noContent().build();
  }
}