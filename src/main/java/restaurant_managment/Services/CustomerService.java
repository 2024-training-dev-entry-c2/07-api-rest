package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Repositories.CustomerRepository;
import restaurant_managment.interfaces.ICustomerService;
import restaurant_managment.Observer.IObserver;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService, IObserver {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<CustomerModel> getAllCustomers() {
    return customerRepository.findAll();
  }
  @Override
  public Optional<CustomerModel> getCustomerById(Long id) {
    return customerRepository.findById(id);
  }

  public CustomerModel createCustomer(CustomerModel customer) {
    return customerRepository.save(customer);
  }

  public CustomerModel updateCustomer(Long id, CustomerModel updatedCustomer) {
    return customerRepository.findById(id)
      .map(customer -> {
        customer.setIsFrequent(updatedCustomer.getIsFrequent());
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        return customerRepository.save(customer);
      })
      .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  public void deleteCustomer(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public void update(String message) {
    System.out.println(message);
  }
}