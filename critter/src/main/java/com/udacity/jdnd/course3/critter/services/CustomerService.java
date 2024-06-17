package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor @Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found customer with id " + id));
    }
}
