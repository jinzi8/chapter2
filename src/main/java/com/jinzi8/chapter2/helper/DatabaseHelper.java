package com.jinzi8.chapter2.helper;

import com.jinzi8.chapter2.model.Customer;
import com.jinzi8.chapter2.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库操作助手类
 */
public class DatabaseHelper {
    /**
     * 线程本地对象，用于保存connection
     * 从而简化数据操作时，操作connection对象的过程
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    /**
     * DBUtils封装了查询操作
     */
    public static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 连接池DBCP
     */
    private static final BasicDataSource DATA_SOURCE;

    /**
     * 创建日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    static {
        //读取配置文件
        Properties config = PropsUtil.loadProps("config.properties");
        String driver = config.getProperty("jdbc.driver");
        String url = config.getProperty("jdbc.url");
        String username = config.getProperty("jdbc.username");
        String password = config.getProperty("jdbc.password");

        //初始化连接池
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(T t) {
        String tableName = getTableName(t.getClass().getSimpleName());
        //遍历所有字段，拼接sql语句
        String sql = "INSERT " + tableName;
        //TODO
        StringBuffer sb = new StringBuffer(sql);
        //参数列表
        List<String> params = new ArrayList<>();
        //字段列表
        StringBuffer fields = new StringBuffer(" (");
        //占位符列表
        StringBuffer strs = new StringBuffer(" VALUES(");
        //反射获取所有字段并遍历
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                //获取字段名
                String name = field.getName();
                name = getTableName(name);
                field.setAccessible(true);
                Object o = field.get(t);
                if (o != null) {
                    //保存参数列表
                    params.add(o.toString());
                    //保存 参数名 占位符
                    fields.append(name + ",");
                    strs.append("?,");
                }
            }
            String fieldsString = fields.substring(0, fields.length() - 1) + " )";
            String strsString = strs.substring(0, strs.length() - 1) + " )";
            sb.append(fieldsString);
            sql = sb.append(strsString).toString();
            System.out.println(sql);
            int i = executeUpdate(sql, params.toArray());
            return i > 0;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("更新操作执行失败,sql语句为：" + sql + ",参数列表为：" + params, e);
            return false;

        }
    }


    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(T t) {
        try {
            String tableName = getTableName(t.getClass().getSimpleName());
            //拼接sql语句
            String sql = "DELETE FROM  " + tableName + " WHERE id = ? ";
            //获取主键id
            Field idField = t.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object id = idField.get(t);
            return executeUpdate(sql, id.toString()) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除失败！", e);
            return false;
        }
    }

    /**
     * 更新实体，约束 主键名必须为id
     */
    public static <T> boolean updateEntity(T t) {
        String tableName = getTableName(t.getClass().getSimpleName());
        //遍历所有字段，拼接sql语句
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE " + tableName + " SET ");
        //参数列表
        List<String> params = new ArrayList<>();
        String sql = "";
        //反射获取所有字段并遍历
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                if (field.getName().equals("id")) {
                    continue;
                }
                String name = field.getName();
                field.setAccessible(true);
                Object o = field.get(t);
                if (o != null) {
                    params.add(o.toString());
                    sb.append(name + " = ? , ");
                }
            }
            Field idFiled = t.getClass().getDeclaredField("id");
            idFiled.setAccessible(true);
            String id = idFiled.get(t).toString();
            params.add(id);
            sql = sb.substring(0, sb.lastIndexOf(",")) + " WHERE id = ?";
            int i = executeUpdate(sql, params.toArray());
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("更新操作执行失败,sql语句为：" + sql + ",参数列表为：" + params, e);
            return false;
        }
    }

    /**
     * 通用增删改方法
     */
    public static int executeUpdate(String sql, Object... params) {
        int row = 0;
        try {
            row = QUERY_RUNNER.update(getConnection(), sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return row;
    }

    /**
     * 实体类名、属性名   转   表名、表字段名
     * 命名规范：实体类为驼峰式，表名为小写，多个单词下划线连接，如：
     * Customer => customer
     * customerDetail => customer_detail
     */
    public static String getTableName(String name) {
        Pattern patter = Pattern.compile("[A-Z]");
        Matcher matcher = patter.matcher(name);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "_" + matcher.group().toLowerCase());
        }
        name = matcher.appendTail(stringBuffer).toString();
        return name.startsWith("_") ? name.substring(1) : name;
    }

    /**
     * 查询单个实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... param) {
        T entity = null;
        try {
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<T>(entityClass), param);
        } catch (Exception e) {
            LOGGER.error("查询单个实体失败", e);
            throw new RuntimeException(e);
        }
        closeConnection();
        return entity;
    }

    /**
     * 查询实体列表
     */
//    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Connection connection, Object... params) {
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection connection = getConnection();
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("查询实体列表失败", e);
            throw new RuntimeException(e);
        }
        closeConnection();
        return entityList;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("获取连接对象失败", e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
//    public static void closeConnection(Connection connection) {
    public static void closeConnection() {
        //关闭当前线程栈中存储的connection对象
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                LOGGER.error("获取连接对象失败", e);
            }
        }
    }
}
