package io.github.cocodx.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cocodx.util.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author amazfit
 * @date 2022-08-28 上午5:01
 **/
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(Result.success()));
    }
}
