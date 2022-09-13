package io.github.cocodx.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author amazfit
 * @date 2022-08-28 下午7:09
 **/
@Data
@Accessors(chain = true)
@ToString
public class Role {

    private Long roleId;
    private String roleName;
    private String authIds;
    private String remarks;
}
