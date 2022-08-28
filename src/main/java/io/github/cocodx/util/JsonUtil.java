package io.github.cocodx.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author amazfit
 * @date 2022-08-28 下午3:51
 **/
public class JsonUtil {

    /**
     * 返回json
     * @param resp
     * @param result
     * @throws IOException
     */
    public static void json(HttpServletResponse resp, Object result) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(result));
    }
}
