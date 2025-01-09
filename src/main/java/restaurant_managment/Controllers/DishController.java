package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Proxy.DishServiceProxy;
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

  private DishService dishService;

  private DishServiceProxy dishServiceProxy;
  private DishDTOConverter dishDTOConverter;

  @Autowired
  public DishController(DishService dishService, DishServiceProxy dishServiceProxy, DishDTOConverter dishDTOConverter) {
    this.dishService = dishService;
    this.dishServiceProxy = dishServiceProxy;
    this.dishDTOConverter = dishDTOConverter;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DishResponseDTO createDish(@RequestBody DishRequestDTO dishRequestDTO) {
    DishModel dishModel = DishDTOConverter.toDish(dishRequestDTO);
    return DishDTOConverter.toDishResponseDTO(dishService.saveDish(dishModel));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DishResponseDTO> getDishById(@PathVariable Long id) {
    Optional<DishModel> dishModel = dishServiceProxy.getDishById(id);
    return dishModel.map(dish -> ResponseEntity.ok(DishDTOConverter.toDishResponseDTO(dish)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<DishResponseDTO>> getAllDishes() {
    List<DishModel> dishes = dishService.getAllDishes();
    List<DishResponseDTO> responseDTOs = dishes.stream()
      .map(DishDTOConverter::toDishResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO) {
    DishModel updatedDishModel = DishDTOConverter.toDish(dishRequestDTO);
    DishModel updatedDish = dishService.updateDish(id, updatedDishModel);
    DishResponseDTO responseDTO = DishDTOConverter.toDishResponseDTO(updatedDish);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
    dishService.deleteDish(id);
    return ResponseEntity.noContent().build();
  }
}