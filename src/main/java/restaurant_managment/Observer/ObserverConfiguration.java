package restaurant_managment.Observer;

import restaurant_managment.Services.CustomerService;
import restaurant_managment.Services.MenuService;
import restaurant_managment.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ObserverConfiguration {

  @Autowired
  private OrderService orderService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private CustomerService customerService;

  @PostConstruct
  public void configureObservers() {
    orderService.addObserver(menuService);
    orderService.addObserver(customerService);
  }
}