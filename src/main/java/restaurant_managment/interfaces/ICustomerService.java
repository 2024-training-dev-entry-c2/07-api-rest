package restaurant_managment.interfaces;

import restaurant_managment.Models.CustomerModel;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
  List<CustomerModel> getAllCustomers();
  Optional<CustomerModel> getCustomerById(Long id);
}