package com.reach52.asssignment.controller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reach52.asssignment.dto.CustomerDTO;
import com.reach52.asssignment.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerDTO customerDTO = null;

    private List<CustomerDTO> customers = null;

    @Before
    public void initializeCustomer()
    {
        customerDTO = new CustomerDTO();
        customerDTO.setAddress("Chennai");
        customerDTO.setName("Jack");
        customerDTO.setDateofBirth(Date.valueOf("2015-03-31"));
        customers = new ArrayList<>();
        customers.add(customerDTO);
    }

    //Testing GET method
    @Test
    public void fetchCustomerTest() throws Exception
    {

        Mockito.when(
                customerService.fetchCustomer()).thenReturn(customers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customers").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, customers);
        byte[] data = out.toByteArray();
        Assert.assertEquals(new String(data),response.getContentAsString());


    }

    //Testing POST method
    @Test
    public void createCustomerTest() throws Exception {


        // setting behaviour for createCustomer of customerservice that is mocked
        Mockito.when(
                customerService.createCustomer(
                        Mockito.any(CustomerDTO.class))).thenReturn(customerDTO.getName()+" added successfully");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);

        // Send Customer as request body to /customers
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customers")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(customerDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals("Jack added successfully", response.getContentAsString());

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());


    }

}