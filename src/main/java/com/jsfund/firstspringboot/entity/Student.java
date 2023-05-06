package com.jsfund.firstspringboot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jsfund.firstspringboot.model.Hobby;
import com.jsfund.firstspringboot.model.HobbyConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Administrator
 * @create 2023/4/30 8:23
 */
@Entity
@Table(name = "t_student")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 40)
    private String uuid;

    @Column(name = "name",columnDefinition = "varchar(100) NOT NULL COMMENT '姓名'")
    private String name;

    @Column(columnDefinition = "int(3) COMMENT '年龄'")
    private Integer age;

    @Column
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "create_time",columnDefinition = "timestamp not null default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "update_time",columnDefinition = "timestamp not null default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Convert(converter = HobbyConverter.class)
    @Column(columnDefinition = "mediumtext comment '兴趣爱好'")
    private Hobby hobby;
}
