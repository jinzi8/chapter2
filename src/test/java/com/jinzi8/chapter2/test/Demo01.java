package com.jinzi8.chapter2.test;

import com.jinzi8.chapter2.model.Customer;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 博哥
 * @create 2019-03-16 11:26
 */
public class Demo01 {
    @Test
    public void m1() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boge", "root", "qweqwe");
        PreparedStatement ppst = conn.prepareStatement(sql);
        ResultSet resultSet = ppst.executeQuery();
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getLong("id"));
            customer.setName(resultSet.getString("name"));
            customer.setContact(resultSet.getString("contact"));
            customer.setTelephone(resultSet.getString("telephone"));
            customer.setEmail(resultSet.getString("email"));
            customer.setRemark(resultSet.getString("remark"));
            customers.add(customer);
            for (Customer customer1 : customers) {
                System.out.println(customer1);
            }

        }
    }
}
