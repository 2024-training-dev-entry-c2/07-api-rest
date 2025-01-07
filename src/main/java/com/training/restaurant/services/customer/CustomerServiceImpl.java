package com.training.restaurant.services.customer;

import com.training.restaurant.dto.customer.CreateCustomerDto;
import com.training.restaurant.dto.customer.UpdateCustomerDto;
import com.training.restaurant.models.Customer;
import com.training.restaurant.models.CustomerType;
import com.training.restaurant.repositories.CustomerRepository;
import com.training.restaurant.services.interfaces.ICustomerService;
import com.training.restaurant.utils.CustomerConverter;
import com.training.restaurant.utils.exceptions.BadRequestException;
import com.training.restaurant.utils.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CreateCustomerDto createCustomerDto) {
        try {
            return customerRepository.save(CustomerConverter.toCreateACustomer(createCustomerDto));
        }catch (DataIntegrityViolationException e){
            String message = "Ocurrio un error al intentar guardar el cliente "+ createCustomerDto.name();
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        try{
            return customerRepository.save(customer);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException("Ocurrio un error al intentar guardar el cliente");
        }
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el cliente con id " + id));
    }

    @Override
    public void removeCustomer(Long id) {
        customerRepository.findById(id).map(c -> {
                    c.setIsActive(false);
                    return customerRepository.save(c);
                }).orElseThrow(() -> new NotFoundException("No se encontro el cliente con id " + id));

    }
    @Override
    public void updateCustomer(Long id, UpdateCustomerDto updateCustomerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Error al intentar actualizar el cliente con el id " + id));

        updateCustomerFields(customer, updateCustomerDto);
        customerRepository.save(customer);
    }

    private void updateCustomerFields(Customer customer, UpdateCustomerDto updateCustomerDto) {
        updateNameIfPresent(customer, updateCustomerDto.name());
        updateEmailIfPresent(customer, updateCustomerDto.email());
        updateTypeIfPresent(customer, updateCustomerDto.type());
        updateAgeIfPresent(customer, updateCustomerDto.age());
        updatePhoneIfPresent(customer, updateCustomerDto.phone());
        updateAddressIfPresent(customer, updateCustomerDto.address());
    }



    private void updateNameIfPresent(Customer customer, String name) {
        if (name != null) {
            customer.setName(name);
        }
    }

    private void updateEmailIfPresent(Customer customer, String email) {
        if (email != null) {
            customer.setEmail(email);
        }
    }

    private void updateTypeIfPresent(Customer customer, String type) {
        if (type != null) {
            customer.setType(CustomerType.fromString(type));
        }
    }

    private void updateAgeIfPresent(Customer customer, Integer age) {
        if (age != null) {
            customer.setAge(age);
        }
    }

    private void updatePhoneIfPresent(Customer customer, String phone) {
        if (phone != null) {
            customer.setPhone(phone);
        }
    }

    private void updateAddressIfPresent(Customer customer, String address) {
        if (address != null) {
            customer.setAddress(address);
        }
    }
}
