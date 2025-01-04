package restaurant_managment.Proxy;

import restaurant_managment.Models.MenuModel;
import restaurant_managment.interfaces.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MenuServiceProxy implements IMenuService {

  @Autowired
  private IMenuService menuService;

  private Map<Long, MenuModel> menuCache = new HashMap<>();
  private List<MenuModel> allMenusCache;

  @Override
  public List<MenuModel> getAllMenus() {
    if (allMenusCache == null) {
      allMenusCache = menuService.getAllMenus();
    }
    return allMenusCache;
  }

  @Override
  public Optional<MenuModel> getMenuById(Long id) {
    if (!menuCache.containsKey(id)) {
      Optional<MenuModel> menu = menuService.getMenuById(id);
      menu.ifPresent(value -> menuCache.put(id, value));
    }
    return Optional.ofNullable(menuCache.get(id));
  }
}