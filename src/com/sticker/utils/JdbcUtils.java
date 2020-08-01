package com.sticker.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;

    //类加载就会执行static代码块
    static {
        try {
            Properties properties = new Properties();
            // 读取 jdbc.properties属性配置文件
            // ClassLoader读取配置文件的默认位置在src文件夹下
            // 所以最好就将配置文件放在src文件夹下
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("mysql5_jdbc.properties");
            // 从流中加载数据
            properties.load(inputStream);
            // 创建 数据库连接 池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

            // System.out.println(dataSource.getConnection());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //用来测试的main方法，因为main方法运行，类才会加载，类加载才会执行static静态代码块
//    public static void main(String[] args) {
//
//    }

    /**
     * 获取数据库连接池的连接
     * @return 如果返回null，说明获取连接失败，否则有值就是获取连接成功
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭当前连接，释放到数据库连接池 并不是真正将连接关闭
     * @param conn
     */
    public static void close(Connection conn) {
        if(conn != null) { //防止空指针异常
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
