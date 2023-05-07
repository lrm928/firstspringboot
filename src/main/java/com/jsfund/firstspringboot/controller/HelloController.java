package com.jsfund.firstspringboot.controller;

import com.jsfund.firstspringboot.common.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @create 2023/4/29 16:12
 */
@RestController
public class HelloController {

    @Value("${student.name}")
    private String name;

    @Value("${student.address}")
    private String address;

    @Autowired
    private ConfigProperties configProperties;

    /***
     * 访问/hello或者/hi任何一个地址，都会返回同样的结果
     * @return
     */
    @RequestMapping(value = {"/hello","/hi"},method = RequestMethod.GET)
    public String say(){
        return "Hello "+name+" address :"+address+" Content: "+configProperties.toString();
    }

}
