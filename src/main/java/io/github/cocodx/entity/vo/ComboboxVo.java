package io.github.cocodx.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 组合框
 * @author amazfit
 * @date 2022-09-11 上午12:46
 **/
@Data
@Accessors(chain = true)
public class ComboboxVo {

    private Integer id;
    private String text;
}
