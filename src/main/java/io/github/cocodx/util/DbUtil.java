package io.github.cocodx.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author amazfit
 * @date 2022-08-28 上午10:03
 **/
public class DbUtil {

    static {
        //加载到系统属性
        Properties properties = System.getProperties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName(System.getProperty("dbDriverName"));
        return DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUserName"), System.getProperty("dbPassword"));
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = connection();
        String sql = "select * from t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String userName = resultSet.getString("user_name");
            String password = resultSet.getString("password");
            System.out.println(userName + password);
        }
    }
}
