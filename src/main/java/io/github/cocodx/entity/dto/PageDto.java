package io.github.cocodx.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * easyui 分页参数
 * @author amazfit
 * @date 2022-09-11 上午4:20
 **/
@Data
@Accessors(chain = true)
public class PageDto {

    /**第几页**/
    private Integer page;

    /**大小**/
    private Integer rows;

    /**
     * 获取limit的开始位置
     * @return
     */
    public Integer getStart(){
        return (page-1)*rows;
    }
}
