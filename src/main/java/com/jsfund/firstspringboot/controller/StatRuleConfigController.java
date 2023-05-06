package com.jsfund.firstspringboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.common.ResponseInfo;
import com.jsfund.firstspringboot.entity.StatRuleConfig;
import com.jsfund.firstspringboot.service.StatRuleConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Administrator
 * @create 2023/5/1 14:47
 */
@Slf4j
@RestController
@RequestMapping("/api/statRule")
public class StatRuleConfigController {

    @Autowired
    private StatRuleConfigService statRuleConfigService;

    @PostMapping(value = "/findList")
    public ResponseInfo findList(@RequestBody(required=false) JSONObject request) {
        Map<String,Object> resultMap =  statRuleConfigService.findList(request==null?new JSONObject().toJSONString():request.toJSONString());
        return new ResponseInfo(resultMap);
    }

    @PostMapping("/save")
    public ResponseInfo<StatRuleConfig> save(@RequestBody StatRuleConfig statRuleConfig){
        log.info("保存配置规则的内容：{}", statRuleConfig);
        ResponseInfo responseInfo = validParam(statRuleConfig);
        if(responseInfo!=null){
            return responseInfo;
        }
        if(StringUtils.isEmpty(statRuleConfig.getId())){//新增
            statRuleConfig.setCreateTime(LocalDateTime.now());
            statRuleConfig.setStatus(Constants.STATUS_YES);
        }
        statRuleConfig.setUpdateTime(LocalDateTime.now());
        StatRuleConfig saveData = statRuleConfigService.saveOrUpdate(statRuleConfig);
        return new ResponseInfo(saveData);
    }

    @RequestMapping(value="/getById",method = RequestMethod.POST)
    public ResponseInfo getById(@RequestBody JSONObject request){
        String id = request.getString("id");
        if(StringUtils.isEmpty(id)){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_ID+Constants.NOT_ALLOW_NULL_MSG);
        }
        StatRuleConfig statRuleConfig = statRuleConfigService.findById(id);
        if (statRuleConfig!=null){
            return new ResponseInfo<StatRuleConfig>(statRuleConfig);
        }else{
            return new ResponseInfo(Constants.NOT_FOUND_DATA_CODE, Constants.NOT_FOUND_DATA_MSG);
        }
    }

    @RequestMapping(value="/deleteById",method = RequestMethod.POST)
    public ResponseInfo deleteById(@RequestBody JSONObject request){
        String id = request.getString("id");
        if(StringUtils.isEmpty(id)){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_ID+Constants.NOT_ALLOW_NULL_MSG);
        }
        StatRuleConfig statRuleConfig = statRuleConfigService.findById(id);
        if(statRuleConfig!=null){
            statRuleConfigService.deleteById(id);
            return new ResponseInfo();
        }else{
            return new ResponseInfo(Constants.NOT_FOUND_DATA_CODE, Constants.NOT_FOUND_DATA_MSG);
        }
    }

    private ResponseInfo validParam(StatRuleConfig statRuleConfig){
        if(StringUtils.isEmpty(statRuleConfig.getTargetName())){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_TARGETNAME+Constants.NOT_ALLOW_NULL_MSG);
        }
        if(StringUtils.isEmpty(statRuleConfig.getRuleType())){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_RULETYPE+Constants.NOT_ALLOW_NULL_MSG);
        }
        if(statRuleConfig.getDefaultVal()==null && StringUtils.isEmpty(statRuleConfig.getSqlRule())){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_SQLRULE+Constants.NOT_ALLOW_NULL_MSG);
        }
        if(statRuleConfigService.exist(statRuleConfig)){
            return new ResponseInfo(Constants.DATA_EXISTED_CODE, Constants.P_TITLE_TARGETNAME+Constants.DATA_EXISTED_MSG);
        }
        return null;
    }

}
