package io.github.cocodx.web;

import io.github.cocodx.dao.RoleDao;
import io.github.cocodx.entity.vo.ComboboxVo;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author amazfit
 * @date 2022-09-11 上午12:41
 **/
public class RoleServlet extends HttpServlet {

    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("comBoList")){
            try {
                comBoList(resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取下拉列表
     * @param resp
     * @throws Exception
     */
    private void comBoList(HttpServletResponse resp)throws Exception{
        List<ComboboxVo> list = roleDao.findList(DbUtil.connection());
        JsonUtil.json(resp,list);
    }

}
