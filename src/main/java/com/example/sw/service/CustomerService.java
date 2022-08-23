package com.example.sw.service;

import com.example.sw.exception.CustomerNotFoundException;
import com.example.sw.dto.request.CustomerRequest;
import com.example.sw.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse save(CustomerRequest customer);

    CustomerResponse update(Long id, CustomerRequest customer) throws CustomerNotFoundException;

    CustomerResponse findById(Long id) throws CustomerNotFoundException;

    void deleteById(Long id);

    List<CustomerResponse> findAll();
}
