package com.example.sw.repository;

import com.example.sw.domain.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {

        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer();

            Long id = i * 100L;

            customer.setId(id);
            customer.setFirstName(id + "fn");
            customer.setMiddleName(id + "mn");
            customer.setLastName(id + "ln");
            customer.setPhone(89850402010L + id);

            customerRepository.save(customer);
        }
    }

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        int actualSize = customerRepository.findAll().size();
        assertEquals(10, actualSize);
    }

    @Test
    public void testFindById() {
        Optional<Customer> customer = customerRepository.findByFirstName("100fn").stream().findFirst();

        assertTrue(customer.isPresent());
    }

    @Test
    public void testFindById_where_id_noExist() {
        Optional<Customer> customer = customerRepository.findById(1000000000L);

        assertTrue(customer.isEmpty());
    }

    @Test
    public void testSaveUser() {
        Customer customer = new Customer();
        customer.setFirstName("test");
        customer.setLastName("test");
        customer.setPhone(1000L);

        customerRepository.save(customer);

        int actualSize = customerRepository.findAll().size();
        assertEquals(11, actualSize);
    }

    @Test
    public void testDeleteCustomer() {
        Optional<Customer> customer = customerRepository.findByFirstName("100fn").stream().findFirst();

        customerRepository.deleteById(customer.get().getId());

        int actualSize = customerRepository.findAll().size();
        assertEquals(9, actualSize);
    }
}