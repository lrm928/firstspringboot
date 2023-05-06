package com.jsfund.firstspringboot.service;

import com.alibaba.fastjson.JSONArray;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.entity.StatRuleConfig;
import com.jsfund.firstspringboot.repository.StatRuleConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计数据服务
 * @author Administrator
 * @create 2023/5/1 20:31
 */
@Service
public class StatDataService {

    @Autowired
    private StatRuleConfigRepository statRuleConfigRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /***
     * 根据指标类型获取对应数据
     * @param targetType 指标类型（必选）
     * @param startTime  统计开始时间（可选）
     * @param endTime    统计结束时间（可选）
     * @return 返回[指标名称,对应数据]
     */
    public Map<String,Object>  getDataByTargetType(String targetType,String startTime,String endTime) {
        List<StatRuleConfig> ruleList = statRuleConfigRepository.getByTargetType(targetType);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ruleList!=null && ruleList.size() > 0) {
            for (StatRuleConfig rule : ruleList) {
                Integer defaultVal = rule.getDefaultVal();
                String sqlRule = rule.getSqlRule();
                if (defaultVal != null) {
                    resultMap.put(rule.getTargetName(), defaultVal);
                } else if (StringUtils.isEmpty(sqlRule)) {
                    continue;
                } else {
                    if(StringUtils.isNotEmpty(startTime)){
                        sqlRule = sqlRule.replaceAll(Constants.VAR_STARTTIME,startTime);
                    }
                    if(StringUtils.isNotEmpty(endTime)){
                        sqlRule = sqlRule.replaceAll(Constants.VAR_ENDTIME,endTime);
                    }
                    Map<String, Object> dataMap = this.queryStatData(rule.getTargetName(),rule.getRuleType(),sqlRule);
                    resultMap.putAll(dataMap);
                }
            }
        }
        return resultMap;
    }

    private Map<String,Object> queryStatData(String targetName,String ruleType,String sqlRule){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String, Object>> dataList = queryListBySql(sqlRule);
        if(dataList!=null && dataList.size()>0){
            if(Constants.RULE_TYPE_VALUE.equals(ruleType)){
                resultMap.put(targetName, dataList.get(0).get("val"));
            }else if(Constants.RULE_TYPE_CHART_DATA.equals(ruleType)){
                resultMap.put(targetName,buildDataForChartByData(dataList));
            }else{
                resultMap.put(targetName, dataList);
            }
        }
        return resultMap;
    }

    public List<Map<String, Object>>  queryListBySql(String sql){
        return this.jdbcTemplate.queryForList(sql);
    }

    /***
     * 根据查询数据构造图形数据格式
     * @param dataList
     * @return
     */
    private Map<String,Object> buildDataForChartByData(List<Map<String,Object>> dataList){
        Map<String,Object> dataMap = new HashMap<>();
        JSONArray xArray = new JSONArray();
        JSONArray  yArray = new JSONArray();

        for(Map<String,Object> map:dataList){
            xArray.add(map.get("xkey"));
            yArray.add(map.get("val"));
        }
        dataMap.put("categories", xArray);
        dataMap.put("datas", yArray);
        return dataMap;
    }
}
