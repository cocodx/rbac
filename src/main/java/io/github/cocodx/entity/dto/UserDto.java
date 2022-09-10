package io.github.cocodx.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author amazfit
 * @date 2022-08-28 上午9:10
 **/
@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

    private String userName;
    private String password;
    private String code;
}
