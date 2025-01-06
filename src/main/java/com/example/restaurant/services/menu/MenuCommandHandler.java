package com.example.restaurant.services.menu;

import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.services.menu.commands.ListMenusCommand;
import com.example.restaurant.services.menu.commands.CreateMenuCommand;
import com.example.restaurant.services.menu.commands.DeleteMenuCommand;
import com.example.restaurant.services.menu.commands.GetMenuByIdCommand;
import com.example.restaurant.services.menu.commands.UpdateMenuCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuCommandHandler {

  private final CreateMenuCommand createMenuCommand;
  private final UpdateMenuCommand updateMenuCommand;
  private final DeleteMenuCommand deleteMenuCommand;
  private final GetMenuByIdCommand getMenuByIdCommand;
  private final ListMenusCommand listOrdersCommand;


  public MenuDTO createCustomer(MenuDTO orderDTO) {
    return createMenuCommand.execute(orderDTO);
  }

  public MenuDTO updateCustomer(MenuDTO orderDTO) {
    return updateMenuCommand.execute(orderDTO.getId(), orderDTO);
  }

  public void deleteCustomer(Long orderId) {
    deleteMenuCommand.execute(orderId);
  }

  public MenuDTO getCustomerById(Long orderId) {
    return getMenuByIdCommand.execute(orderId);
  }

  public List<MenuDTO> listCustomers() {
    return listOrdersCommand.execute();
  }
}
