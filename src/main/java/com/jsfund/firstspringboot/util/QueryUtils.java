package com.jsfund.firstspringboot.util;

import com.jsfund.firstspringboot.util.query.AttributeBean;
import com.jsfund.firstspringboot.util.query.Criteria;
import com.jsfund.firstspringboot.util.query.QueryCriteriaBean;
import com.jsfund.firstspringboot.util.query.Restrictions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * 查询工具类
 * @author Administrator
 * @create 2023/4/30 21:05
 */
public class QueryUtils {
    public static final String PARAM_ORDER_DESC = "DESC";

    /***
     * EQ, NE, LIKE, GT, LT, GTE, LTE
     */
    public static final String PARAM_QUERY_EQ = "EQ";
    public static final String PARAM_QUERY_LIKE = "LIKE";
    public static final String PARAM_QUERY_NE = "NE";
    public static final String PARAM_QUERY_GT = "GT";
    public static final String PARAM_QUERY_LT = "LT";
    public static final String PARAM_QUERY_GTE = "GTE";
    public static final String PARAM_QUERY_LTE = "LTE";
    public static final String PARAM_QUERY_IN = "IN";	//in
    public static final String PARAM_QUERY_NIN = "NIN"; //not in

    /***
     * 构造查询条件
     *
     * @param data
     * @return
     */
    public static <T> Criteria<T> buildCriteria(QueryCriteriaBean data) {
        Criteria<T> criteria = new Criteria<T>();
        if (data!=null && data.getWhereList() != null && data.getWhereList().size() > 0) {
            List<AttributeBean> whereList = data.getWhereList();
            for (AttributeBean attr : whereList) {
                String key = attr.getKey();
                String opt = StringUtils.isNotEmpty(attr.getOpt())?attr.getOpt().toUpperCase():PARAM_QUERY_EQ;
                String value = attr.getVal();
                if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
                    continue;
                Object val = value;
                if(DateTools.isDate(value)){
                    if(String.valueOf(value).length()<=10){
                        value = value+" 00:00:00";
                    }
                    val = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }else if(DateTools.isTimestamp(value)){
                    val = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                if (PARAM_QUERY_LIKE.equals(opt)) {
                    criteria.add(Restrictions.like(key, value));
                } else if (PARAM_QUERY_NE.equals(opt)) {
                    criteria.add(Restrictions.ne(key, val));
                } else if (PARAM_QUERY_GT.equals(opt)) {
                    criteria.add(Restrictions.gt(key, val));
                } else if (PARAM_QUERY_LT.equals(opt)) {
                    criteria.add(Restrictions.lt(key, val));
                } else if (PARAM_QUERY_LTE.equals(opt)) {
                    criteria.add(Restrictions.lte(key, val));
                } else if (PARAM_QUERY_GTE.equals(opt)) {
                    criteria.add(Restrictions.gte(key, val));
                } else if (PARAM_QUERY_IN.equals(opt)) {
                    criteria.add(Restrictions.in(key, Arrays.asList(value.split(",")), true));
                } else if (PARAM_QUERY_NIN.equals(opt)) {
                    criteria.add(Restrictions.notin(key, Arrays.asList(value.split(",")), true));
                } else {
                    criteria.add(Restrictions.eq(key, val));
                }
            }
        }
        return criteria;
    }

    /**
     * 构造分页与排序内容
     * @param data
     * @return
     */
    public static PageRequest buildPageRequest(QueryCriteriaBean data) {
        Sort sort = null;
        Integer pageNum = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(data!=null){
            AttributeBean order = data.getOrder();
            if (order != null && !StringUtils.isEmpty(order.getKey())) {
                if(PARAM_ORDER_DESC.equalsIgnoreCase(order.getVal())){
                    sort = Sort.by(Sort.Direction.DESC, order.getKey().split(","));
                }else{
                    sort = Sort.by(Sort.Direction.ASC, order.getKey().split(","));
                }
            }
            pageNum = data.getPageNum()==null?pageNum:data.getPageNum();
            pageSize = data.getPageSize()==null?pageSize:data.getPageSize();
        }
        if(sort == null){
            return PageRequest.of(pageNum - 1, pageSize);
        }else{
            return PageRequest.of(pageNum - 1, pageSize, sort);
        }
    }

}
