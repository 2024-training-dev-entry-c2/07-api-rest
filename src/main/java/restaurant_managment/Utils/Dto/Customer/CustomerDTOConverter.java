package restaurant_managment.Utils.Dto.Customer;

import org.springframework.stereotype.Component;
import restaurant_managment.Models.CustomerModel;

@Component
public class CustomerDTOConverter {

  public static CustomerModel toCustomer(CustomerRequestDTO dto) {
    CustomerModel customer = new CustomerModel();
    customer.setFirstName(dto.getFirstName());
    customer.setLastName(dto.getLastName());
    customer.setEmail(dto.getEmail());
    customer.setPhone(dto.getPhone());
    return customer;
  }

  public static CustomerResponseDTO toCustomerResponseDTO(CustomerModel customer) {
    CustomerResponseDTO dto = new CustomerResponseDTO();
    dto.setId(customer.getId());
    dto.setFirstName(customer.getFirstName());
    dto.setLastName(customer.getLastName());
    dto.setEmail(customer.getEmail());
    dto.setPhone(customer.getPhone());
    dto.setIsFrequent(customer.getIsFrequent());
    return dto;
  }
}