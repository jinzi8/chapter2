package com.jinzi8.chapter2.service;

import com.jinzi8.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    /**
     * 获取客户列表
     * @param keyword 关键字
     * @return 客户列表
     */
    public List<Customer> getCustomerList(String keyword){
        //TODO0
         return null;
    }

    /**
     * 获取客户
     * @param id
     * @return
     */
    public Customer getCustomer(long id){
        //TODO
        return null;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }
    /**
     * 更新客户
     */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        //TODO
        return false;
    }
    /**
     *  删除客户
     */
    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }
}
