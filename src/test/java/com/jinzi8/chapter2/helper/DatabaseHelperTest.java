package com.jinzi8.chapter2.helper;

import com.jinzi8.chapter2.model.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 博哥
 * @create 2019-03-16 22:18
 */
public class DatabaseHelperTest {
    @Test
    public void m1(){
        Customer c = new Customer();
        c.setId(11);
        c.setName("东南皮革厂");
        c.setContact("黄鹤");
        c.setEmail("abc@qq.com");
        boolean b = DatabaseHelper.insertEntity(c);
        System.out.println(b);
    }

}