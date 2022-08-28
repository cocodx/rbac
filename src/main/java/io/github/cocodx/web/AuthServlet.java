package io.github.cocodx.web;

import io.github.cocodx.dao.AuthDao;
import io.github.cocodx.entity.Auth;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.util.JsonUtil;
import io.github.cocodx.util.Result;
import io.github.cocodx.vo.AuthVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amazfit
 * @date 2022-08-28 下午3:19
 **/
public class AuthServlet extends HttpServlet {

    private AuthDao authDao = new AuthDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auth> auths;
        try {
            auths = authDao.findTreeList(DbUtil.connection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<AuthVo> treeData = copyVo(auths);
        JsonUtil.json(resp, treeData);
    }

    public static List<AuthVo> copyVo(List<Auth> auths) {
        List<AuthVo> collect = auths.stream().map(item -> {
            AuthVo authVo = new AuthVo();
            authVo.setId(item.getAuthId())
                    .setText(item.getAuthName())
                    .setIconCls(item.getIconCls())
                    .setState(item.getState() == 0 ? "closed" : "open")
                    .setChecked(false)
            ;
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                authVo.setChildren(copyVo(item.getChildren()));
            }
            return authVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
