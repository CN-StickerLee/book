package com.sticker.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;

    //创建一个ThreadLocal对象
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

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
        //Connection conn = null;
        Connection conn = conns.get();

        if (conn == null) {
            try {
                conn = dataSource.getConnection();//从数据库连接池中获取连接
                //将此数据库连接保存到 ThreadLocal对象中，供后面的 jdbc操作使用
                //使得大家都用同一个数据库连接，保证事务的一致性（业务涉及的操作能成都成，否则都失败.例如生成订单操作）
                conns.set(conn);
                conn.setAutoCommit(false); //将此数据库连接设置为手动管理事务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交 事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        //1）其实这里提交了事务之后，说明订单整个操作已经成功，当前ThreadLocal保
        //存的连接池已经不需要了，下一次操作的又是连接池的一个新的数据库连接，所以可以将其移除掉。
       //2）一直不移除的话，线程池可能发生溢出。
        conns.remove();
    }

    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.rollback();//回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        //1）其实这里回滚了事务之后，说明订单整个操作已经失败，当前ThreadLocal保
        //存的连接池已经不需要了，下一次操作的又是连接池的一个新的数据库连接，所以可以将其移除掉。
        //2）一直不移除的话，线程池可能发生溢出。
        conns.remove();
    }

    /**
     * 关闭当前连接，释放到数据库连接池 并不是真正将连接关闭
     * @param conn
     */
//    public static void close(Connection conn) {
//        if(conn != null) { //防止空指针异常
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
