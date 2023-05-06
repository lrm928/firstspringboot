package com.jsfund.firstspringboot.service;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.entity.StatRuleConfig;
import com.jsfund.firstspringboot.repository.StatRuleConfigRepository;
import com.jsfund.firstspringboot.util.QueryUtils;
import com.jsfund.firstspringboot.util.query.Criteria;
import com.jsfund.firstspringboot.util.query.QueryCriteriaBean;
import com.jsfund.firstspringboot.util.query.Restrictions;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 统计规则配置服务
 * @author Administrator
 * @create 2023/5/1 14:07
 */
@Service
public class StatRuleConfigService {

    @Autowired
    private StatRuleConfigRepository statRuleConfigRepository;

    public StatRuleConfigService(StatRuleConfigRepository statRuleConfigRepository) {
        this.statRuleConfigRepository = statRuleConfigRepository;
    }

    public Map<String,Object> findList(String queryCriteria){
        QueryCriteriaBean data = JSONObject.parseObject(queryCriteria, QueryCriteriaBean.class);
        Pageable pageable = QueryUtils.buildPageRequest(data);
        Criteria<StatRuleConfig> criteria = QueryUtils.buildCriteria(data);
        Page<StatRuleConfig> page = statRuleConfigRepository.findAll(criteria,pageable);
        List<StatRuleConfig> dataList = page.getContent();
        Map<String,Object> result = new HashMap<>();
        result.put(Constants.PARAM_COUNT, page.getTotalElements());
        result.put(Constants.PARAM_LIST, dataList);
        return result;
    }

    @Transactional
    public StatRuleConfig saveOrUpdate(StatRuleConfig statRuleConfig){
        StatRuleConfig saveData = statRuleConfigRepository.save(statRuleConfig);
        return saveData;
    }

    @Transactional
    public void deleteById(String id){
        statRuleConfigRepository.deleteById(id);
    }

    public boolean exist(StatRuleConfig statRuleConfig){
        Criteria<StatRuleConfig> criteria = new Criteria<StatRuleConfig>();
        criteria.add(Restrictions.eq("targetName", statRuleConfig.getTargetName()));
        criteria.add(Restrictions.ne("status", Constants.STATUS_YES));
        if(StringUtils.isNotEmpty(statRuleConfig.getId())){
            criteria.add(Restrictions.ne("id", statRuleConfig.getId()));
        }
        Optional<StatRuleConfig> data = this.statRuleConfigRepository.findOne(criteria);
        return data.isPresent() ? true : false;
    }

    public StatRuleConfig findById(String id){
        Optional<StatRuleConfig> build = this.statRuleConfigRepository.findById(id);
        return build.isPresent() ? build.get() : null;
    }

}
