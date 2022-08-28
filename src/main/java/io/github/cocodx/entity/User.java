package io.github.cocodx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author amazfit
 * @date 2022-08-28 上午8:47
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    /**
     * 主键
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 备注
     */
    private String remarks;

}
