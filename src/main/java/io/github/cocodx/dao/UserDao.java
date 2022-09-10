package io.github.cocodx.dao;

import io.github.cocodx.entity.User;
import io.github.cocodx.entity.dto.UserUpdatePasswordDto;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.entity.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author amazfit
 * @date 2022-08-28 上午8:49
 **/
public class UserDao {

    public User login(UserDto userDto, Connection connection) throws SQLException {
        String sql = "select * from t_user where user_name=? and password=?";
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userDto.getUserName());
            preparedStatement.setString(2, userDto.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserName(resultSet.getString("user_name"))
                        .setPassword(resultSet.getString("password"))
                        .setUserType(resultSet.getInt("user_type"))
                        .setRoleId(resultSet.getLong("role_id"))
                        .setRemarks(resultSet.getString("remarks"))
                        .setUserId(resultSet.getLong("user_id"))
                ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.closeConnection(connection);
        }
        return user;
    }

    /**
     * 修改用户密码
     * @param userUpdate
     * @param connection
     */
    public void updatePassword(UserUpdatePasswordDto userUpdate, Connection connection) throws SQLException {
        String sql = "update t_user set password=? where user_name=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userUpdate.getNewPassword());
            preparedStatement.setString(2, userUpdate.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.closeConnection(connection);
        }
    }
}
