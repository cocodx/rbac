package io.github.cocodx.dao;

import io.github.cocodx.entity.Role;
import io.github.cocodx.entity.dto.PageDto;
import io.github.cocodx.entity.vo.ComboboxVo;
import io.github.cocodx.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ComboboxVo> findList(Connection connection)throws Exception {
        String sql = "SELECT * FROM t_role";
        List<ComboboxVo> list = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Role> roleList = new ArrayList<>();
            while (resultSet.next()){
                Role role = new Role();
                role.setRoleId(resultSet.getLong("role_id"))
                        .setRoleName(resultSet.getString("role_name"))
                        .setAuthIds(resultSet.getString("auth_ids"))
                        .setRemarks(resultSet.getString("remarks"))
                ;
                roleList.add(role);
            }
            list = roleList.stream().map(item->{
                ComboboxVo comboboxVo = new ComboboxVo();
                comboboxVo.setId(item.getRoleId().intValue())
                        .setText(item.getRoleName())
                        ;
                return comboboxVo;
            }).collect(Collectors.toList());
        }catch (SQLException e){
            throw new SQLException(e);
        }finally {
            if (connection!=null){
                connection.close();
            }
        }
        return list;
    }

    public List<Role> findRoleList(String keywords, PageDto pageDto, Connection connection)throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM t_role");
        sql.append(" where 1=1");
        if (StringUtils.isNotBlank(keywords)) {
            sql.append(" and (role_name like "+ StrUtil.likeStr(keywords) +" or remarks like "+ StrUtil.likeStr(keywords) +")");
        }
        sql.append(" limit "+pageDto.getStart()+","+pageDto.getRows());
        List<Role> roleList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setRoleId(resultSet.getLong("role_id"))
                        .setRoleName(resultSet.getString("role_name"))
                        .setAuthIds(resultSet.getString("auth_ids"))
                        .setRemarks(resultSet.getString("remarks"))
                ;
                roleList.add(role);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }finally {
            if (connection!=null){
                connection.close();
            }
        }
        return roleList;
    }
}
