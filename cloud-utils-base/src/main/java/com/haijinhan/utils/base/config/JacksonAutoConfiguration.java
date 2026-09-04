package com.haijinhan.utils.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * 通用 Jackson 配置
 * 适用于 Spring Boot 3.x/4.x 甚至纯 Spring 环境
 * 不依赖 Jackson2ObjectMapperBuilderCustomizer
 */
@Configuration
public class JacksonAutoConfiguration {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    public ObjectMapper objectMapper() {
        // 1. 创建 JavaTimeModule 并配置格式化器
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT);

        javaTimeModule.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer(dtf));
        javaTimeModule.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer(dtf));
        javaTimeModule.addSerializer(java.time.LocalDate.class, new LocalDateSerializer(df));
        javaTimeModule.addDeserializer(java.time.LocalDate.class, new LocalDateDeserializer(df));

        // 2. 创建 ObjectMapper 实例
        ObjectMapper mapper = new ObjectMapper();

        // 注册时间模块
        mapper.registerModule(javaTimeModule);

        // 禁用时间戳格式，使用字符串
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}