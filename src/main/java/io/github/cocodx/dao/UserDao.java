package io.github.cocodx.dao;

import io.github.cocodx.entity.User;
import io.github.cocodx.entity.dto.UserSaveDto;
import io.github.cocodx.entity.dto.UserUpdatePasswordDto;
import io.github.cocodx.entity.vo.UserVo;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.entity.dto.UserDto;
import io.github.cocodx.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<UserVo> findUserList(String s_userName, String s_roleId, Connection connection) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT u.*,r.role_name FROM t_user u LEFT JOIN t_role r ON u.role_id=r.role_id");
        sql.append(" where 1=1");
        if (StringUtils.isNotBlank(s_userName)){
            sql.append(" and u.user_name like "+ StrUtil.likeStr(s_userName));
        }
        if (StringUtils.isNotBlank(s_roleId)){
            sql.append(" and u.role_id = "+s_roleId);
        }

        List<UserVo> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserVo user = new UserVo();
                user.setUserName(resultSet.getString("user_name"))
                        .setPassword(resultSet.getString("password"))
                        .setUserType(resultSet.getInt("user_type"))
                        .setRoleId(resultSet.getLong("role_id"))
                        .setRemarks(resultSet.getString("remarks"))
                        .setUserId(resultSet.getLong("user_id"))
                        .setRoleName(resultSet.getString("role_name"))
                ;
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DbUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * 根据用户名称查找数量
     * @param userName
     * @param connection
     * @return
     */
    public Integer countByUserName(String userName, Connection connection) {
        String sql = "select count(*) as count from t_user where user_name = ?";
        Integer count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DbUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    public Integer updateUser(UserSaveDto userSaveDto, Connection connection) {
        String sql = "update t_user set user_name=?,password=?,user_type=?,role_id=?,remarks=? where user_id=?";
        Integer count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userSaveDto.getUserName());
            preparedStatement.setString(2, userSaveDto.getPassword());
            preparedStatement.setInt(3, userSaveDto.getUserType());
            preparedStatement.setLong(4, userSaveDto.getRoleId());
            preparedStatement.setString(5, userSaveDto.getRemarks());
            preparedStatement.setLong(6, userSaveDto.getUserId());
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DbUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    /**
     * 保存用户
     * @param userSaveDto
     * @param connection
     * @return
     */
    public Integer saveUser(UserSaveDto userSaveDto, Connection connection) {
        String sql = "insert into t_user values (null,?,?,?,?,?)";
        Integer count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userSaveDto.getUserName());
            preparedStatement.setString(2, userSaveDto.getPassword());
            preparedStatement.setInt(3, userSaveDto.getUserType());
            preparedStatement.setLong(4, userSaveDto.getRoleId());
            preparedStatement.setString(5, userSaveDto.getRemarks());
            boolean execute = preparedStatement.execute();
            if (execute){
                count = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DbUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }
}
