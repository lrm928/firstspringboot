package com.jsfund.firstspringboot.repository;

import com.jsfund.firstspringboot.entity.StatRuleConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 * @create 2023/5/1 14:06
 */
public interface StatRuleConfigRepository extends JpaRepository<StatRuleConfig, String>, JpaSpecificationExecutor<StatRuleConfig> {

    @Query("select t from StatRuleConfig t where t.targetType = ?1 ")
    List<StatRuleConfig> getByTargetType(String targetType);

    @Query("select t from StatRuleConfig t where t.targetName = ?1 ")
    StatRuleConfig getByTargetName(String targetName);
}
