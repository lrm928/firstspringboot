package com.jsfund.firstspringboot;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.util.query.AttributeBean;
import com.jsfund.firstspringboot.util.query.QueryCriteriaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @create 2023/5/1 9:22
 */
public class BuildQueryTest {

    public static void main(String[] args) {
        buildQueryCondition();
    }

    public static void buildQueryCondition(){
        QueryCriteriaBean query = new QueryCriteriaBean();
        query.setPageNum(1);
        query.setPageSize(3);

        List<AttributeBean> whereList = new ArrayList<AttributeBean>();
        AttributeBean condition1 = new AttributeBean();
        condition1.setKey("age");
        condition1.setOpt("gt");
        condition1.setVal("9");
        whereList.add(condition1);

        query.setWhereList(whereList);

        AttributeBean order = new AttributeBean();
        order.setKey("age");
        order.setVal("desc");

        query.setOrder(order);

        System.out.println(JSONObject.toJSONString(query));

    }
}