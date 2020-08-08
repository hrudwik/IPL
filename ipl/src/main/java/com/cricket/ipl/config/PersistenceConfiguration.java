package com.cricket.ipl.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cricket.ipl.dao.mybatis.mapper")
public class PersistenceConfiguration {
}
