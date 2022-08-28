package io.github.cocodx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author amazfit
 * @date 2022-08-28 下午2:26
 **/
@Data
@Accessors(chain = true)
public class Auth {

    private Long authId;
    private String authName;
    private String authPath;
    private Long parentId;
    private String remarks;
    private Integer state;
    private String iconCls;

    private List<Auth> children;
}
