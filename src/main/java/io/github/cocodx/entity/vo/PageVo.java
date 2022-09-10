package io.github.cocodx.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * eaysui 表格接收类型
 * @author amazfit
 * @date 2022-09-10 下午11:44
 **/
@Data
@Accessors(chain = true)
public class PageVo<T> implements Serializable {

    private Integer total;

    private T rows;
}
