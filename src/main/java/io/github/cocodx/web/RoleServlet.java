package io.github.cocodx.web;

import io.github.cocodx.dao.RoleDao;
import io.github.cocodx.entity.Role;
import io.github.cocodx.entity.dto.PageDto;
import io.github.cocodx.entity.vo.ComboboxVo;
import io.github.cocodx.entity.vo.PageVo;
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
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action.equals("comBoList")){
            try {
                comBoList(resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (action.equals("list")){
            try {
                list(req,resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (action.equals("save")){
            try {
                save(req,resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 保存角色
     * @param req
     * @param resp
     * @throws Exception
     */
    private void save(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String roleId = req.getParameter("roleId");
        String roleName = req.getParameter("roleName");
        String authIds = req.getParameter("authIds");
        String remarks = req.getParameter("remarks");
        Role role = new Role().setRoleName(roleName).setAuthIds(authIds).setRemarks(remarks);
        System.out.println(role);
    }

    /**
     * 分页
     * @param req
     * @param resp
     */
    private void list(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        String page = req.getParameter("page");
        String rows = req.getParameter("rows");
        String keywords = req.getParameter("keywords");
        PageDto pageDto = new PageDto();
        pageDto.setPage(Integer.parseInt(page));
        pageDto.setRows(Integer.parseInt(rows));
        List<Role> roleList = roleDao.findRoleList(keywords,pageDto,DbUtil.connection());
        PageVo<List<Role>> pageVo = new PageVo<>();
        pageVo.setTotal(roleList.size())
                .setRows(roleList)
                ;
        JsonUtil.json(resp,pageVo);
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
