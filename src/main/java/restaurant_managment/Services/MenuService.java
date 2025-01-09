package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Repositories.MenuRepository;
import restaurant_managment.interfaces.IMenuService;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService implements IMenuService {

  @Autowired
  MenuRepository menuRepository;

  @Autowired
  DishRepository dishRepository;

  public List<MenuModel> getAllMenus() {
    return menuRepository.findAll();
  }

  @Override
  public Optional<MenuModel> getMenuById(Long id) {
    return menuRepository.findById(id);
  }

  public MenuModel createMenu(MenuModel menu, List<Long> dishIds) {
    List<DishModel> dishes = dishRepository.findAllById(dishIds);
    if (dishes.size() != dishIds.size()) {
      throw new RuntimeException("One or more dishes not found");
    }
    menu.setDishes(dishes);
    return menuRepository.save(menu);
  }

  public MenuModel updateMenu(Long id, MenuModel updatedMenu, List<Long> dishIds) {
    return menuRepository.findById(id)
      .map(menu -> {
        menu.setName(updatedMenu.getName());
        List<DishModel> dishes = dishRepository.findAllById(dishIds);
        if (dishes.size() != dishIds.size()) {
          throw new RuntimeException("One or more dishes not found");
        }
        menu.setDishes(dishes);
        return menuRepository.save(menu);
      })
      .orElseThrow(() -> new RuntimeException("Menu not found"));
  }

  public void deleteMenu(Long id) {
    menuRepository.deleteById(id);
  }
}