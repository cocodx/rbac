package io.github.cocodx.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author amazfit
 * @date 2022-08-28 下午2:27
 **/
@Data
@Accessors(chain = true)
public class AuthVo implements Serializable {

    private Long id;
    private String text;
    private String iconCls;
    private String state;
    private Map<String,Object> attributes;

    private Boolean checked;
    private List<AuthVo> children;



}
