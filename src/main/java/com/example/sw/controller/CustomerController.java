package com.example.sw.controller;

import com.example.sw.exception.CustomerNotFoundException;
import com.example.sw.dto.request.CustomerRequest;
import com.example.sw.dto.response.CustomerResponse;
import com.example.sw.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/customers")
@Api("Controller for customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ApiOperation("get all customers")
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("get customer by id")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        CustomerResponse customerResponse;
        try {
            customerResponse = customerService.findById(id);
        } catch (CustomerNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("create customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse;
        try {
            customerResponse = customerService.save(customerRequest);
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update customer by id")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse;
        try {
            customerResponse = customerService.update(id, customerRequest);
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CustomerNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete customer by id")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
        } catch (CustomerNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
