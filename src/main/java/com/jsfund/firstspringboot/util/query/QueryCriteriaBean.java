package com.jsfund.firstspringboot.util.query;

import lombok.Data;

import java.util.List;

/**
 * 查询参数配置
 * @author Administrator
 * @create 2023/4/30 20:59
 */
@Data
public class QueryCriteriaBean {

    /***
     * 页码
     */
    private Integer pageNum = 1;

    /***
     * 页大小
     */
    private Integer pageSize = Integer.MAX_VALUE;

    /***
     * 查询条件
     */
    private List<AttributeBean> whereList;

    /***
     * 排序条件
     */
    private AttributeBean order;

}
