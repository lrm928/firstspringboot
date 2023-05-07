package com.jsfund.firstspringboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Administrator
 * @create 2023/4/29 16:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "student")
public class ConfigProperties {

    private String name;

    private String action;

    private int age;

    private List<String> hobby;

}
