package com.jinzi8.chapter2.controller;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
public class CustomerCreateServlet extends javax.servlet.http.HttpServlet {
    /**
     * 进入创建客户界面
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    //TODO
    }

    /**
     * 处理创建客户请求
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //TODO
        System.out.println(123123);
        response.getWriter().write("abcde");
    }
}
