package com.example.sw.service;

import com.example.sw.exception.CustomerNotFoundException;
import com.example.sw.domain.Customer;
import com.example.sw.dto.request.CustomerRequest;
import com.example.sw.dto.response.CustomerResponse;
import com.example.sw.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse save(CustomerRequest customerRequest) {
        checkCustomerRequest(customerRequest);

        Customer customer = createCustomer(customerRequest);

        customerRepository.save(customer);

        return createCustomerResponse(customer);
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest customerRequest) throws CustomerNotFoundException {

        if (!Objects.nonNull(customerRequest)) {
            throw new IllegalArgumentException("[Customer] not be null");
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id: " + id + " not found");
        }

        Customer customer = optionalCustomer.get();

        updateCustomer(customer, customerRequest);

        customerRepository.update(customer);

        return createCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findById(Long id) {
        if (!Objects.nonNull(id)) {
            throw new IllegalArgumentException("[id] not be null");
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id: " + id + " not found");
        }

        return createCustomerResponse(optionalCustomer.get());
    }

    @Override
    public void deleteById(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id: " + id + " not found");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::createCustomerResponse)
                .collect(Collectors.toList());
    }

    private void checkCustomerRequest(CustomerRequest customerRequest) {
        if (!Objects.nonNull(customerRequest)) {
            throw new IllegalArgumentException("[Customer] not be null");
        }
        if (!StringUtils.hasText(customerRequest.getFirstName())) {
            throw new IllegalArgumentException("[Customer.firstname] not be null or empty");
        }
        if (!StringUtils.hasText(customerRequest.getLastName())) {
            throw new IllegalArgumentException("[Customer.lastname] not be null or empty");
        }
    }

   private void updateCustomer(Customer customer, CustomerRequest customerRequest) {
       if (StringUtils.hasText(customerRequest.getFirstName())) {
           customer.setFirstName(customerRequest.getFirstName());
       }

       if (StringUtils.hasText(customerRequest.getMiddleName())) {
           customer.setMiddleName(customerRequest.getMiddleName());
       }

       if (StringUtils.hasText(customerRequest.getLastName())) {
           customer.setLastName(customerRequest.getLastName());
       }

       if (Objects.nonNull(customerRequest.getPhone())) {
           customer.setPhone(customerRequest.getPhone());
       }
   }

    private Customer createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        updateCustomer(customer, customerRequest);
        return customer;
   }

    private CustomerResponse createCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getLastName(),
                customer.getPhone()
        );
    }
}
