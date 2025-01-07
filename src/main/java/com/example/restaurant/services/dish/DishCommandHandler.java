package com.example.restaurant.services.dish;

import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.services.dish.commands.CreateDishCommand;
import com.example.restaurant.services.dish.commands.DeleteDishCommand;
import com.example.restaurant.services.dish.commands.GetDishByIdCommand;
import com.example.restaurant.services.dish.commands.ListDishesCommand;
import com.example.restaurant.services.dish.commands.UpdateDishCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishCommandHandler {

  private final CreateDishCommand createDishCommand;
  private final UpdateDishCommand updateDishCommand;
  private final DeleteDishCommand deleteDishCommand;
  private final GetDishByIdCommand getDishByIdCommand;
  private final ListDishesCommand listDishesCommand;


  public DishResponseDTO createDish(DishRequestDTO dishDTO) {
    return createDishCommand.execute(dishDTO);
  }

  public DishResponseDTO updateDish(Long id, DishRequestDTO dishDTO) {
    return updateDishCommand.execute(id, dishDTO);
  }

  public void deleteDish(Long dishId) {
    deleteDishCommand.execute(dishId);
  }

  public DishResponseDTO getDishById(Long dishId) {
    return getDishByIdCommand.execute(dishId);
  }

  public List<DishResponseDTO> listDishes() {
    return listDishesCommand.execute();
  }
}
