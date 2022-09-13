package io.github.cocodx.web;

import io.github.cocodx.dao.AuthDao;
import io.github.cocodx.dao.RoleDao;
import io.github.cocodx.entity.Auth;
import io.github.cocodx.entity.Role;
import io.github.cocodx.entity.vo.UserVo;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.util.JsonUtil;
import io.github.cocodx.vo.AuthVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author amazfit
 * @date 2022-08-28 下午3:19
 **/
public class AuthServlet extends HttpServlet {

    private AuthDao authDao = new AuthDao();
    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action.equals("tree")){
            try {
                tree(req,resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (action.equals("treeChecked")){
            try {
                treeChecked(req,resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    /**
     * 加载所有菜单，标记已选菜单
     * @param req
     * @param resp
     */
    private void treeChecked(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        //1.加载所有菜单，不进行过滤
        //2.标记checked
        String flag = req.getParameter("flag");
        if (flag.equals("false")){
            //添加不需要checked，直接返回所有
            List<Auth> auths = authDao.findTreeList(DbUtil.connection());
            List<AuthVo> treeData = copyVo(auths);
            JsonUtil.json(resp, treeData);
        }else{

        }
    }

    /**
     * 左侧菜单
     * @param req
     * @param resp
     * @throws Exception
     */
    private void tree(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        List<Auth> auths;
        HashSet<Long> authIdSet = new HashSet<>();
        try {
            UserVo user = (UserVo) req.getSession().getAttribute("currentUser");
            addIdSet(authIdSet,roleDao.findRoleById(user.getRoleId(), DbUtil.connection()));
            auths = authDao.findTreeList(DbUtil.connection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<AuthVo> treeData = copyVo(auths);
        List<AuthVo> filteredTreeData = filterData(treeData,authIdSet);
        JsonUtil.json(resp, filteredTreeData);
    }

    /**
     * 对树形结构数据，进行过滤
     * @param treeData
     * @param authIdSet
     * @return
     */
    private List<AuthVo> filterData(List<AuthVo> treeData,HashSet<Long> authIdSet) {
        //满足为true的收集
        List<AuthVo> collect = treeData.stream()
                .map(item->{
                    if (authIdSet.contains(item.getId())){
                        if (item.getChildren()!=null && item.getChildren().size()>0){
                            List<AuthVo> authVos = filterData(item.getChildren(), authIdSet);
                            item.setChildren(authVos);
                        }
                        return item;
                    }
                    return null;
                })
                .filter(Objects::nonNull).collect(Collectors.toList());
        return collect;
    }

    /**
     * 将role角色中的authId，将装载进hashset中
     * @param authIdSet
     * @param roleById
     */
    private void addIdSet(HashSet<Long> authIdSet, Role roleById) {
        String authIds = roleById.getAuthIds();
        String[] split = authIds.split(",");
        for (String authId:split){
            authIdSet.add(Long.parseLong(authId.trim()));
        }
    }

    public static List<AuthVo> copyVo(List<Auth> auths) {
        List<AuthVo> collect = auths.stream().map(item -> {
            AuthVo authVo = new AuthVo();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("authPath",item.getAuthPath());
            authVo.setId(item.getAuthId())
                    .setText(item.getAuthName())
                    .setIconCls(item.getIconCls())
                    .setState(item.getState() == 0 ? "closed" : "open")
                    .setChecked(false)
                    .setAttributes(hashMap)
            ;
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                authVo.setChildren(copyVo(item.getChildren()));
            }
            return authVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
