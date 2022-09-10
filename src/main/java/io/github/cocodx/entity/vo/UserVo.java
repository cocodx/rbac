package io.github.cocodx.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author amazfit
 * @date 2022-09-10 上午9:43
 **/
@Data
@Accessors(chain = true)
public class UserVo {

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

    /**
     * 角色名称用于主界面显示
     */
    private String roleName;
}
