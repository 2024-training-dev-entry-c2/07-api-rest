package com.example.restaurant.services.dish;

import com.example.restaurant.models.dto.DishDTO;
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


  public DishDTO createCustomer(DishDTO dishDTO) {
    return createDishCommand.execute(dishDTO);
  }

  public DishDTO updateCustomer(Long id, DishDTO dishDTO) {
    return updateDishCommand.execute(id, dishDTO);
  }

  public void deleteCustomer(Long customerId) {
    deleteDishCommand.execute(customerId);
  }

  public DishDTO getCustomerById(Long customerId) {
    return getDishByIdCommand.execute(customerId);
  }

  public List<DishDTO> listCustomers() {
    return listDishesCommand.execute();
  }
}
