package io.github.cocodx.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cocodx.dao.UserDao;
import io.github.cocodx.entity.User;
import io.github.cocodx.util.DbUtil;
import io.github.cocodx.util.Result;
import io.github.cocodx.util.StrUtil;
import io.github.cocodx.vo.UserVo;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author amazfit
 * @date 2022-08-28 上午5:01
 **/
public class UserServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("code")) {
            code(req, resp);
        }
        if (action.equals("login")) {
            try {
                login(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (action.equals("main")){
            req.getRequestDispatcher("main.jsp").forward(req,resp);
        }


    }

    /**
     * 登录
     *
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("code");
        UserVo userVo = new UserVo().setUserName(userName).setPassword(password).setCode(verifyCode);


        ObjectMapper objectMapper = new ObjectMapper();
        String code = (String) request.getSession().getAttribute("code");
        if (!userVo.getCode().equalsIgnoreCase(code)) {
            json(response, Result.fail("温馨提示：验证码错误！"));
        }

        User user = userDao.login(userVo, DbUtil.connection());
        if (user == null) {
            json(response, Result.fail("温馨提示：用户名或密码错误，未找到此用户！"));
        }else{
            json(response, Result.success("登录成功！"));
        }
    }

    /**
     * 返回验证码
     *
     * @param req
     * @param resp
     */
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //创建空白图片
        BufferedImage bufferedImage = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
        //获取图片画笔
        Graphics graphics = bufferedImage.getGraphics();
        Random random = new Random();
        //设置画笔颜色
        graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        //绘制矩形背景
        graphics.fillRect(0, 0, 100, 30);
        //绘制8条干扰线
        for (int i = 0; i < 8; i++) {
            graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            graphics.drawLine(random.nextInt(100), random.nextInt(30), random.nextInt(100), random.nextInt(30));
        }
        String codeStr = StrUtil.randomStr(5);
        //存到session里面
        req.getSession().setAttribute("code", codeStr);

        graphics.setFont(new Font(null, Font.ITALIC + Font.BOLD, 24));
        //当xy为0，在左下角
        graphics.drawString(codeStr, 5, 25);

        resp.setContentType("image/jpeg");
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(bufferedImage, "jpeg", outputStream);
        outputStream.close();
    }

    public static void json(HttpServletResponse resp, Result result) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(result));
    }
}
