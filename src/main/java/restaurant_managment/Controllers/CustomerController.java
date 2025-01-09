package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Proxy.CustomerServiceProxy;
import restaurant_managment.Utils.Dto.Customer.CustomerRequestDTO;
import restaurant_managment.Utils.Dto.Customer.CustomerResponseDTO;
import restaurant_managment.Utils.Dto.Customer.CustomerDTOConverter;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Services.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  private final CustomerServiceProxy customerServiceProxy;

  @Autowired
  public CustomerController(CustomerService customerService, CustomerServiceProxy customerServiceProxy) {
    this.customerServiceProxy = customerServiceProxy;
    this.customerService = customerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
    CustomerModel customerModel = CustomerDTOConverter.toCustomer(customerRequestDTO);
    CustomerModel createdCustomer = customerService.createCustomer(customerModel);
    return CustomerDTOConverter.toCustomerResponseDTO(createdCustomer);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
    Optional<CustomerModel> customerModel = customerServiceProxy.getCustomerById(id);
    return customerModel.map(customer -> ResponseEntity.ok(CustomerDTOConverter.toCustomerResponseDTO(customer)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
    List<CustomerModel> customers = customerService.getAllCustomers();
    List<CustomerResponseDTO> responseDTOs = customers.stream()
      .map(CustomerDTOConverter::toCustomerResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
    CustomerModel updatedCustomerModel = CustomerDTOConverter.toCustomer(customerRequestDTO);
    CustomerModel updatedCustomer = customerService.updateCustomer(id, updatedCustomerModel);
    CustomerResponseDTO responseDTO = CustomerDTOConverter.toCustomerResponseDTO(updatedCustomer);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}