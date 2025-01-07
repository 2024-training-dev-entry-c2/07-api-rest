package com.example.restaurant.services.menu;

import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.services.menu.commands.ListMenusCommand;
import com.example.restaurant.services.menu.commands.CreateMenuCommand;
import com.example.restaurant.services.menu.commands.DeleteMenuCommand;
import com.example.restaurant.services.menu.commands.GetMenuByIdCommand;
import com.example.restaurant.services.menu.commands.UpdateMenuCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCommandHandler {

  private final CreateMenuCommand createMenuCommand;
  private final UpdateMenuCommand updateMenuCommand;
  private final DeleteMenuCommand deleteMenuCommand;
  private final GetMenuByIdCommand getMenuByIdCommand;
  private final ListMenusCommand listOrdersCommand;


  public MenuResponseDTO createMenu(MenuRequestDTO orderDTO) {
    return createMenuCommand.execute(orderDTO);
  }

  public MenuResponseDTO updateMenu(Long id, MenuRequestDTO orderDTO) {
    return updateMenuCommand.execute(id, orderDTO);
  }

  public void deleteMenu(Long orderId) {
    deleteMenuCommand.execute(orderId);
  }

  public MenuResponseDTO getMenuById(Long orderId) {
    return getMenuByIdCommand.execute(orderId);
  }


  public List<MenuResponseDTO> listMenus() {
    return listOrdersCommand.execute();
  }
}
