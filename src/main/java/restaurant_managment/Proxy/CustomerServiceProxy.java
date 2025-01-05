package restaurant_managment.Proxy;

import restaurant_managment.Models.CustomerModel;
import restaurant_managment.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomerServiceProxy implements ICustomerService {

  @Autowired
  private ICustomerService customerService;

  private Map<Long, CustomerModel> customerCache = new HashMap<>();

  @Override
  public Optional<CustomerModel> getCustomerById(Long id) {
    if (!customerCache.containsKey(id)) {
      Optional<CustomerModel> customer = customerService.getCustomerById(id);
      customer.ifPresent(value -> customerCache.put(id, value));
    }
    return Optional.ofNullable(customerCache.get(id));
  }
}