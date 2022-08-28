package io.github.cocodx.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author amazfit
 * @date 2022-08-28 上午5:02
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
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
        Result result = new Result().setCode(200).setMsg("succcess");
        return result;
    }

    public static Result fail() {
        Result result = new Result().setCode(500).setMsg("fail");
        return result;
    }

    public static Result success(String msg) {
        Result success = success();
        success.setMsg(msg);
        return success;
    }

    public static Result fail(String msg) {
        Result fail = fail();
        fail.setMsg(msg);
        return fail;
    }
}
