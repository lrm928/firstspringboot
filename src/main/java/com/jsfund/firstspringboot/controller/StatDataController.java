package com.jsfund.firstspringboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.common.ResponseInfo;
import com.jsfund.firstspringboot.service.StatDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Administrator
 * @create 2023/5/1 22:18
 */
@Slf4j
@RestController
@RequestMapping("/api/statData")
public class StatDataController {

    @Autowired
    private StatDataService statDataService;

    @RequestMapping(value="/getDataByTargetType",method = RequestMethod.POST)
    public ResponseInfo getDataByTargetType(@RequestBody JSONObject request){
        String targetType = request.getString("targetType");
        String startTime = request.getString("startTime");
        String endTime = request.getString("endTime");
        if(StringUtils.isEmpty(targetType)){
            return new ResponseInfo(Constants.NOT_ALLOW_NULL_CODE, Constants.P_TITLE_RULETYPE+Constants.NOT_ALLOW_NULL_MSG);
        }
        Map<String,Object> resultMap = statDataService.getDataByTargetType(targetType,startTime,endTime);
        return new ResponseInfo(resultMap);
    }
}
