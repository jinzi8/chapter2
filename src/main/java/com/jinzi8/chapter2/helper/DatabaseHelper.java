package com.jinzi8.chapter2.helper;

import com.jinzi8.chapter2.util.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public class DatabaseHelper {
    /**
     * 创建日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties config = PropsUtil.loadProps("config.properties");
        DRIVER = config.getProperty("jdbc.driver");
        URL = config.getProperty("jdbc.url");
        USERNAME = config.getProperty("jdbc.username");
        PASSWORD = config.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("不能加载jdbc驱动", e);
        }
    }

    public static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,Connection connection, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("查询实体列表失败",e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
        return entityList;
    }
    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("获取连接对象失败", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                LOGGER.error("获取连接对象失败", e);
            }
        }
    }

    /**
     * 阿弥陀佛，大佬保佑，永无bug
     */
    public void test() {
        System.out.println("永无bug");
    }

}
