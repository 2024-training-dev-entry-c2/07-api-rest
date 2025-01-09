package restaurant_managment.Proxy;

import restaurant_managment.Models.MenuModel;
import restaurant_managment.interfaces.IMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuServiceProxyTest {

  @Mock
  private IMenuService menuService;

  @InjectMocks
  private MenuServiceProxy menuServiceProxy;

  private MenuModel menu;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    menu = new MenuModel(1L, "Lunch Menu", null);
  }

  @Test
  void testGetMenuById_MenuNotInCache() {
    when(menuService.getMenuById(1L)).thenReturn(Optional.of(menu));

    Optional<MenuModel> result = menuServiceProxy.getMenuById(1L);

    assertTrue(result.isPresent());
    assertEquals(menu, result.get());

    verify(menuService, times(1)).getMenuById(1L);
  }

  @Test
  void testGetMenuById_MenuInCache() {
    when(menuService.getMenuById(1L)).thenReturn(Optional.of(menu));

    menuServiceProxy.getMenuById(1L);

    Optional<MenuModel> result = menuServiceProxy.getMenuById(1L);

    assertTrue(result.isPresent());
    assertEquals(menu, result.get());

    verify(menuService, times(0)).getMenuById(1L);
  }
}
