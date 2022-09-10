package io.github.cocodx.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 保存 ，编辑 dto
 * @author amazfit
 * @date 2022-09-11 上午1:35
 **/
@Data
@Accessors(chain = true)
public class UserSaveDto {

    private Long userId;

    private String userName;

    private String password;

    private Integer userType=2;

    private Long roleId;

    private String remarks;
}
