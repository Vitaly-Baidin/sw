package com.example.sw.repository;

import com.example.sw.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    void save(Customer customer);

    void update(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findByFirstName(String name);

    void deleteById(Long id);

    void deleteAll();

    List<Customer> findAll();
}
