package com.jsfund.firstspringboot.model;

import com.alibaba.fastjson.JSONObject;
import jakarta.persistence.AttributeConverter;

/**
 * @author Administrator
 * @create 2023/4/30 9:54
 */
public class HobbyConverter  implements AttributeConverter<Hobby, String> {
    @Override
    public String convertToDatabaseColumn(Hobby hobbyAttribute) {
        return JSONObject.toJSONString(hobbyAttribute);
    }

    @Override
    public Hobby convertToEntityAttribute(String dbData) {
        return JSONObject.parseObject(dbData,Hobby.class);
    }
}
