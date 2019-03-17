package com.jinzi8.chapter2.service;

import com.jinzi8.chapter2.helper.DatabaseHelper;
import com.jinzi8.chapter2.model.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void setUp() throws Exception {
        //TODO 初始化数据库
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCustomerList() {
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(4, customerList.size());
    }

    @Test
    public void getCustomer() {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
        System.out.println(customer);
    }
    @Test
    public void createCustomer(){
        Customer c = new Customer();
        c.setId(11);
        c.setName("东南皮革厂");
        c.setContact("黄鹤");
        c.setEmail("abc@qq.com");
        boolean customer = customerService.createCustomer(c);
        System.out.println(customer);
    }

}