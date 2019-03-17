package com.jinzi8.chapter2.service;

import com.jinzi8.chapter2.helper.DatabaseHelper;
import com.jinzi8.chapter2.model.Customer;
import com.jinzi8.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    /**
     * 创建日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);


    /**
     * 获取客户列表
     *
     * @return 客户列表
     */
    public List<Customer> getCustomerList() {
        return DatabaseHelper.queryEntityList(Customer.class, "SELECT * FROM customer");
/*       Connection connection = DatabaseHelper.getConnection();
/       try {
            String sql = "SELECT * FROM customer";

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
            }

            return DatabaseHelper.queryEntityList(Customer.class, sql);
        } catch (Exception e) {
            LOGGER.error("执行sql语句失败", e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return null;
*/
    }

    /**
     * 获取客户
     *
     * @param id
     * @return
     */
    public Customer getCustomer(long id) {
        Customer customer = DatabaseHelper.queryEntity(Customer.class, "select * from customer where id = ?", id);
        return customer;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Customer customer) {
        return DatabaseHelper.insertEntity(customer);
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(Customer customer) {
        return DatabaseHelper.updateEntity(customer);
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(Customer customer) {
        return DatabaseHelper.deleteEntity(customer);
    }
}
