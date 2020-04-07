package com.reach52.asssignment.service;

import com.reach52.asssignment.dto.CustomerDTO;
import com.reach52.asssignment.entity.CustomerEntity;
import com.reach52.asssignment.exceptions.InvalidImageException;
import com.reach52.asssignment.exceptions.NoSuchCustomerException;
import com.reach52.asssignment.repository.CustomerRepository;
import com.reach52.asssignment.translator.CustomerTranslator;
import com.reach52.asssignment.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerTranslator translator;

    @Autowired
    private Validator validator;

    public ResponseEntity<CustomerDTO> findById(Long userId) {
        CustomerEntity customer = customerRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not available for Id :" + userId));

        return ResponseEntity.ok().body(translator.getCustomer(customer));
    }

    public List<CustomerDTO> fetchCustomer() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (CustomerEntity customer : customerEntities) {
            customerDTOs.add(translator.getCustomer(customer));
        }
        return customerDTOs;
    }

    public String createCustomer(CustomerDTO customerDTO, MultipartFile file) throws InvalidImageException {
        CustomerEntity customerEntity = translator.getCustomerEntity(customerDTO);
        if (Objects.nonNull(file)) {
            if (!validator.isValidImageFile(file)) {

                throw new InvalidImageException("Only jpeg and png images allowed");
            } else {
                try {
                    customerEntity.setUserImage(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        CustomerEntity customer = customerRepository.saveAndFlush(customerEntity);
        return customer.getName() + " got persisted successfully";
    }

    public CustomerDTO updateCustomer(long userId, CustomerDTO customerDTO) {
        CustomerEntity customer = customerRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not available for Id :" + userId));
        if (customerDTO.getName() != null)
            customer.setName(customerDTO.getName());
        if (customerDTO.getAddress() != null)
            customer.setAddress(customerDTO.getAddress());

        CustomerEntity customerReceived = customerRepository.saveAndFlush(customer);
        return translator.getCustomer(customerReceived);
    }

    public String deleteCustomer(@PathVariable("userId") long userId) throws NoSuchCustomerException {
        String response = null;
        CustomerEntity customer = customerRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not available for Id :" + userId));
        customerRepository.delete(customer);
        response = customer.getName() + " with user id " + userId + " got deleted successfully";
        return response;
    }

}
