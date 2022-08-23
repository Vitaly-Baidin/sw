package com.example.sw.repository;

import com.example.sw.domain.Customer;
import com.example.sw.mapper.CustomerMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String SAVE_CUSTOMER = "insert into customers(first_name,middle_name,last_name,phone) values (?,?,?,?)";

    private final String UPDATE_CUSTOMER = "update customers set first_name=?, middle_name=?, last_name=?, phone=? where id = ?";

    private final String FIND_BY_ID_CUSTOMER = "select * from customers where id=?";

    private final String FIND_BY_FIRSTNAME_CUSTOMERS = "select * from customers where first_name = ?";

    private final String DELETE_BY_ID_CUSTOMER = "delete from customers where id=?";

    private final String DELETE_ALL = "delete from customers";

    private final String FIND_ALL_CUSTOMERS = "select * from customers";

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        jdbcTemplate.update(
                SAVE_CUSTOMER,
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getLastName(),
                customer.getPhone()
        );
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update(
                UPDATE_CUSTOMER,
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getId()
        );
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jdbcTemplate.query(
                FIND_BY_ID_CUSTOMER,
                new CustomerMapper(),
                id
        ).stream().findFirst();
    }

    @Override
    public List<Customer> findByFirstName(String name) {
        return jdbcTemplate.query(
                FIND_BY_FIRSTNAME_CUSTOMERS,
                BeanPropertyRowMapper.newInstance(Customer.class),
                name
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                DELETE_BY_ID_CUSTOMER,
                id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                FIND_ALL_CUSTOMERS,
                BeanPropertyRowMapper.newInstance(Customer.class)
        );
    }
}
