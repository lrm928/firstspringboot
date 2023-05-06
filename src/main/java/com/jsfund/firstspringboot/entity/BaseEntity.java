package com.jsfund.firstspringboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础实体
 * @author Administrator
 * @create 2023/5/1 13:58
 */
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 40)
    public String id;

    /***
     * 状态 1有效，0-无效
     */
    @Column(columnDefinition = "varchar(2)  default '1' comment '状态'")
    public String status;

    /***
     * 创建用户
     */
    @Column(name = "create_user",updatable = false, columnDefinition = "varchar(40) comment '创建用户'")
    private String createUser;


    @Column(name = "create_time",columnDefinition = "timestamp not null default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "update_time",columnDefinition = "timestamp not null default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
