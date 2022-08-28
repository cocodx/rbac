package io.github.cocodx.dao;

import io.github.cocodx.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author amazfit
 * @date 2022-08-28 下午7:11
 **/
public class RoleDao {

    public Role findRoleById(Long roleId,Connection connection) throws SQLException {
        String sql = "SELECT * FROM t_role WHERE role_id="+roleId;
        Role role = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                role = new Role();
                role.setRoleId(resultSet.getLong("role_id"))
                        .setRoleName(resultSet.getString("role_name"))
                        .setAuthIds(resultSet.getString("auth_ids"))
                        .setRemarks(resultSet.getString("remarks"))
                ;
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }finally {
            if (connection!=null){
                connection.close();
            }
        }
        return role;
    }
}
