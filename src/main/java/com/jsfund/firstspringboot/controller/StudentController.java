package com.jsfund.firstspringboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.common.ResponseInfo;
import com.jsfund.firstspringboot.entity.Student;
import com.jsfund.firstspringboot.service.StudentService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2023/4/30 8:30
 */
@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value="/getByUuid",method = RequestMethod.POST)
    public ResponseInfo<Student> getByUuid(@RequestBody JSONObject request){
        String uuid = request.getString("uuid");
        Student student = studentService.findByUuid(uuid);
        ResponseInfo responseInfo = null;
        if(student==null){
            responseInfo = new ResponseInfo(Constants.NOT_FOUND_DATA_CODE,Constants.NOT_FOUND_DATA_MSG);
        }else{
            responseInfo = new ResponseInfo<Student>(student);
        }
        return responseInfo;
    }

    @GetMapping("/findAllList")
    public ResponseInfo findAllList(){
        return new ResponseInfo<List<Student>>(studentService.findALLList());
    }

    @PostMapping(value = "/findList")
    public ResponseInfo findALLList(@RequestBody(required=false) JSONObject request) {
        Map<String,Object> resultMap = studentService.findList(request==null?new JSONObject().toJSONString():request.toJSONString());
        return new ResponseInfo(resultMap);
    }

    @RequestMapping(value="/getByName",method = RequestMethod.POST)
    public ResponseInfo getByName(@RequestBody JSONObject request){
        String name = request.getString("name");
        Student student = studentService.findByName(name);
        if(student==null){
            return new ResponseInfo(Constants.NOT_FOUND_DATA_CODE,Constants.NOT_FOUND_DATA_MSG);
        }else{
            log.info("查询学生{}的详细信息：{}", name, student);
            return new ResponseInfo<Student>(student);
        }
    }

    @PostMapping("/saveStudent")
    public ResponseInfo<Student> saveStudent(@RequestBody Student student){
        log.info("保存学生信息参数：{}", student);
        ResponseInfo responseInfo = null;
        if(StringUtils.isEmpty(student.getName())){
            responseInfo = new ResponseInfo(Constants.NOT_PARAM_NULL_CODE,Constants.NOT_PARAM_NULL_MSG);
        }else{
            if(student.getCreateTime()==null){
                student.setCreateTime(LocalDateTime.now());
            }
            student.setUpdateTime(LocalDateTime.now());
            Student saveStudent = studentService.save(student);
            log.info("保存后的学生信息：{}", saveStudent);
            responseInfo = new ResponseInfo<Student>(saveStudent);
        }
        return responseInfo;
    }

    @RequestMapping(value="/deleteByUuid",method = RequestMethod.POST)
    public ResponseInfo deleteByUuid(@RequestBody JSONObject request){
        String uuid = request.getString("uuid");
        ResponseInfo responseInfo = null;
        if(!studentService.exist(uuid)){
            responseInfo = new ResponseInfo(Constants.NOT_FOUND_DATA_CODE,Constants.NOT_FOUND_DATA_MSG);
        }else{
            studentService.deleteByUuid(uuid);
            responseInfo = new ResponseInfo();
        }
        return responseInfo;
    }
}
