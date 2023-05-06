package com.jsfund.firstspringboot.service;

import com.alibaba.fastjson.JSONObject;
import com.jsfund.firstspringboot.common.Constants;
import com.jsfund.firstspringboot.entity.Student;
import com.jsfund.firstspringboot.repository.StudentRepository;
import com.jsfund.firstspringboot.util.QueryUtils;
import com.jsfund.firstspringboot.util.query.Criteria;
import com.jsfund.firstspringboot.util.query.QueryCriteriaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2023/4/30 17:44
 * @noinspection ALL
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findByUuid(String uuid){
        return studentRepository.findByUuid(uuid);
    }

    public Student findByName(String name){
        return studentRepository.findByName(name);
    }

    public List<Student> findALLList(){
        return studentRepository.findAll();
    }

    public Student save(Student student){
        return studentRepository.save(student);
    }

    public void deleteByUuid(String uuid){
        studentRepository.deleteById(uuid);
    }

    public boolean exist(String uuid){
        return studentRepository.existsById(uuid);
    }

    public Map<String,Object> findList(String queryCriteria){
        QueryCriteriaBean data = JSONObject.parseObject(queryCriteria, QueryCriteriaBean.class);
        Pageable pageable = QueryUtils.buildPageRequest(data);
        Criteria<Student> criteria = QueryUtils.buildCriteria(data);
        Page<Student> page = studentRepository.findAll(criteria,pageable);
        List<Student> dataList = page.getContent();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constants.PARAM_COUNT, page.getTotalElements());
        result.put(Constants.PARAM_LIST, dataList);
        return result;
    }
}
