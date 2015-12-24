package com.goeuro.dot.base;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = {"com.goeuro.dot.base"})
public class BaseConfig {


    @Bean
    @Qualifier(value = "goeuroTemplate")
    public JdbcTemplate createGoeuroTrmplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/goeuro?useUnicode=true&amp;characterEncoding=UTF-8");
        dataSource.setUsername("goeuro");
        dataSource.setPassword("IaxE_zSguULyRVw");
        return new JdbcTemplate(dataSource);
    }
}
