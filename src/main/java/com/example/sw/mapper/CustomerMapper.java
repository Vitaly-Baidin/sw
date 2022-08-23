package com.example.sw.mapper;

import com.example.sw.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setMiddleName(rs.getString("middle_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setPhone(rs.getLong("phone"));
        return customer;
    }
}
