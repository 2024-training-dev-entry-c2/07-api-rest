package restaurant_managment.interfaces;

import restaurant_managment.Models.MenuModel;

import java.util.List;
import java.util.Optional;

public interface IMenuService {
  List<MenuModel> getAllMenus();
  Optional<MenuModel> getMenuById(Long id);
}