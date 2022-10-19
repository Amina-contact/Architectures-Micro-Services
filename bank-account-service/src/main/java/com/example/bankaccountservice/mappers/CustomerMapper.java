package com.example.bankaccountservice.mappers;

import com.example.bankaccountservice.dto.CustomerDTO;
import com.example.bankaccountservice.entities.Customer;
import org.springframework.beans.BeanUtils;

public class CustomerMapper {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        //Prendre les proprietés du customer et les copier dans customerDTO
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
}
