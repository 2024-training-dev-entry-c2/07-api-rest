package com.example.restaurant.services.dish;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.services.dish.commands.CreateDishCommand;
import com.example.restaurant.services.dish.commands.DeleteDishCommand;
import com.example.restaurant.services.dish.commands.GetDishByIdCommand;
import com.example.restaurant.services.dish.commands.ListDishesCommand;
import com.example.restaurant.services.dish.commands.UpdateDishCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishCommandHandler {

  private final CreateDishCommand createDishCommand;
  private final UpdateDishCommand updateDishCommand;
  private final DeleteDishCommand deleteDishCommand;
  private final GetDishByIdCommand getDishByIdCommand;
  private final ListDishesCommand listDishesCommand;


  public DishDTO createCustomer(DishDTO dishDTO) {
    return createDishCommand.execute(dishDTO);
  }

  public DishDTO updateCustomer(DishDTO dishDTO) {
    return updateDishCommand.execute(dishDTO);
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
