package util;

import entity.Departments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcV1 {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=webcoban;encrypt=false";
    static String username = "sa";
    static String password = "123123";

    static {
        try { // nạp driver
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**Mở kết nối*/
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, username, password);
    }
    /**Thao tác dữ liệu*/
    public static int executeUpdate(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }
    /**Truy vấn dữ liệu*/
    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}
