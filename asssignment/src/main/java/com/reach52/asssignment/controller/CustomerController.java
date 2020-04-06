package com.reach52.asssignment.controller;

import com.reach52.asssignment.dto.CustomerDTO;
import com.reach52.asssignment.dto.ErrorMessage;
import com.reach52.asssignment.exceptions.NoSuchCustomerException;
import com.reach52.asssignment.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Api(value = "CustomerController, REST APIs that deal with Customer DTO")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getUserById(@PathVariable(value = "id") Long userId) throws NoSuchCustomerException {
        return customerService.findById(userId);
    }

    //Fetching customer details
    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Fetch all the customers", response = CustomerDTO.class, tags="fetchCustomer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched the customers successfully"),
            @ApiResponse(code = 404, message = "Customer details not found") })
    public List<CustomerDTO> fetchCustomer() {
        return customerService.fetchCustomer();
    }


    //Adding a customer
    @PostMapping(consumes = "application/json")
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerDTO customerDTO, Errors errors) {
        String response = "";
        if (errors.hasErrors()) {
            //collecting the validation errors of all fields together in a String delimited by commas
            response = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            ErrorMessage error = new ErrorMessage();
            error.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
            error.setMessage(response);
            return ResponseEntity.ok(error);
        } else {
            response = customerService.createCustomer(customerDTO);
            return ResponseEntity.ok(response);
        }

    }

    //Updating an existing customer
    @PutMapping(value = "/{userId}", consumes = "application/json")
    public CustomerDTO updateCustomer(@PathVariable("userId") long userId, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(userId, customerDTO);
    }


    // Deleting a customer
    @DeleteMapping(value = "/{userId}", produces = "text/html")
    public String deleteCustomer(
            @PathVariable("userId") long userId)
            throws NoSuchCustomerException {

        return customerService.deleteCustomer(userId);

    }
}