package io.github.cocodx.util;

import io.github.cocodx.vo.AuthVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author amazfit
 * @date 2022-08-28 上午5:02
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 返回数据
     */
    private T data;

    /**
     * code
     */
    private Integer code;

    /**
     * msg
     */
    private String msg;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        return new Result().setCode(200).setMsg("success");
    }

    public static Result fail() {
        return new Result().setCode(500).setMsg("fail");
    }

    public Result<T> success(T data){
        Result<T> success = new Result<T>();
        success.setData(data);
        return success;
    }

    public static Result success(String msg) {
        return success().setMsg(msg);
    }

    public static Result fail(String msg) {
        return fail().setMsg(msg);
    }
}
