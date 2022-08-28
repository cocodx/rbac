package io.github.cocodx.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author amazfit
 * @date 2022-08-28 上午9:10
 **/
@Data
@Accessors(chain = true)
public class UserVo implements Serializable {

    private String userName;
    private String password;
    private String code;
}
