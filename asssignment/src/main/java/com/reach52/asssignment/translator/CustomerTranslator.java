package com.reach52.asssignment.translator;

import com.reach52.asssignment.dto.CustomerDTO;
import com.reach52.asssignment.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerTranslator {
	
	public CustomerEntity getCustomerEntity(CustomerDTO customer) {
		CustomerEntity customerEntity = CustomerEntity.builder()
				.name(customer.getName())
				.address(customer.getAddress())
				.dateofBirth(customer.getDateofBirth())
				.build();
		
		return customerEntity;
	}
	
	public CustomerDTO getCustomer(CustomerEntity customerEntity) {
		CustomerDTO customer = CustomerDTO.builder()
				.userId(customerEntity.getUserId())
				.name(customerEntity.getName())
				.address(customerEntity.getAddress())
				.dateofBirth(customerEntity.getDateofBirth())
				.build();
		
		return customer;
	}
}
