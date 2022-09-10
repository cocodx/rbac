package io.github.cocodx.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 修改密码
 * @author amazfit
 * @date 2022-09-10 下午9:15
 **/
@Data
@Accessors(chain = true)
public class UserUpdatePasswordDto implements Serializable {
    /**用户名**/
    private String userName;
    /**老密码**/
    private String password;
    /**新密码**/
    private String newPassword;
    /**确认密码**/
    private String yesPassword;
}
