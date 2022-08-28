package io.github.cocodx.dao;

import io.github.cocodx.entity.Auth;
import io.github.cocodx.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amazfit
 * @date 2022-08-28 下午2:26
 **/
public class AuthDao {


    public List<Auth> findTreeList(Connection connection) throws SQLException {
        ArrayList<Auth> auths = new ArrayList<>();
        Integer parentId = -1;
        String sql = "select * from t_auth where parent_id="+parentId;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Auth auth = new Auth();
            auth.setAuthId(resultSet.getLong("auth_id"))
                    .setAuthName(resultSet.getString("auth_name"))
                    .setAuthPath(resultSet.getString("auth_path"))
                    .setParentId(resultSet.getLong("parent_id"))
                    .setRemarks(resultSet.getString("remarks"))
                    .setState(resultSet.getInt("state"))
                    .setIconCls(resultSet.getString("iconCls"))
                    ;
            auths.add(auth);
        }
        findChildren(auths);
        return auths;
    }

    public List<Auth> findTreeListByParentId(Connection connection,Long parentId) throws SQLException {
        List<Auth> auths = new ArrayList<>();
        String sql = "select * from t_auth where parent_id="+parentId;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Auth auth = new Auth();
            auth.setAuthId(resultSet.getLong("auth_id"))
                    .setAuthName(resultSet.getString("auth_name"))
                    .setAuthPath(resultSet.getString("auth_path"))
                    .setParentId(resultSet.getLong("parent_id"))
                    .setRemarks(resultSet.getString("remarks"))
                    .setState(resultSet.getInt("state"))
                    .setIconCls(resultSet.getString("iconCls"))
            ;
            auths.add(auth);
        }
        return findChildren(auths);
    }

    /**
     * 查找子集菜单
     * @param auths
     */
    private List<Auth> findChildren(List<Auth> auths) {
        List<Auth> collect = auths.stream().map(item -> {
            List<Auth> sonList;
            try {
                sonList = findTreeListByParentId(DbUtil.connection(), item.getAuthId());
                if (sonList.size()>0 && sonList!=null){
                    item.setChildren(sonList);
                    findChildren(sonList);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return item;
        }).collect(Collectors.toList());
        return collect;
    }


}
