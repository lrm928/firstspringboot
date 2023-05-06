package com.jsfund.firstspringboot.util.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个查询条件容器
 * @author Administrator
 * @create 2023/4/30 22:20
 */
public class Criteria<T> implements Specification<T> {

    private static final long serialVersionUID = 1L;
    private List<Criterion> criterions = new ArrayList<Criterion>();

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for(Criterion c : criterions){
                predicates.add(c.toPredicate(root, query,builder));
            }
            query.distinct(true);
            // 将所有条件用 and 联合起来
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    /***
     * 增加简单条件表达式
     * @param criterion
     */
    public void add(Criterion criterion){
        if(criterion!=null){
            criterions.add(criterion);
        }
    }
}

