package com.jsfund.firstspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统计规则配置
 * @author Administrator
 * @create 2023/5/1 13:57
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "stat_rule_config")
@EqualsAndHashCode(callSuper = true)
public class StatRuleConfig extends BaseEntity {

    /***
     * 指标分类
     */
    @Column(name = "target_type", columnDefinition = "varchar(40)  comment '指标分类'")
    private String targetType;

    /***
     * 指标名称
     */
    @Column(name = "target_name", columnDefinition = "varchar(40)  comment '指标名称'")
    private String targetName;

    /***
     * 指标描述
     */
    @Column(name = "target_desc", columnDefinition = "varchar(200)  comment '指标描述'")
    private String targetDesc;

    /***
     * 规则返回数据类型 [val-统计值|list-数据列表]
     */
    @Column(name = "rule_type", columnDefinition = "varchar(40)  comment '规则类型'")
    private String ruleType;

    /***
     * SQL规则
     */
    @Column(name = "sql_rule", columnDefinition = "varchar(2000)  comment 'SQL规则'")
    private String sqlRule;

    /***
     * 默认值-[sqlType=value时有效]
     */
    @Column(name = "default_val", columnDefinition = "int(11)  comment '默认值'")
    private Integer defaultVal;
}
