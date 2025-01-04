package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.MenuRepository;
import restaurant_managment.interfaces.IMenuService;
import restaurant_managment.Observer.IObserver;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService implements IMenuService, IObserver {

  @Autowired
  private MenuRepository menuRepository;

  @Override
  public List<MenuModel> getAllMenus() {
    return menuRepository.findAll();
  }

  @Override
  public Optional<MenuModel> getMenuById(Long id) {
    return menuRepository.findById(id);
  }

  public MenuModel createMenu(MenuModel menu) {
    return menuRepository.save(menu);
  }

  public MenuModel updateMenu(Long id, MenuModel updatedMenu) {
    return menuRepository.findById(id)
      .map(menu -> {
        menu.setName(updatedMenu.getName());
        menu.setDishes(updatedMenu.getDishes());
        return menuRepository.save(menu);
      })
      .orElseThrow(() -> new RuntimeException("Menu not found"));
  }

  public void deleteMenu(Long id) {
    menuRepository.deleteById(id);
  }

  @Override
  public void update(String message) {
    // Lógica para manejar la actualización cuando se notifica un cambio en OrderService
    System.out.println(message);
  }
}