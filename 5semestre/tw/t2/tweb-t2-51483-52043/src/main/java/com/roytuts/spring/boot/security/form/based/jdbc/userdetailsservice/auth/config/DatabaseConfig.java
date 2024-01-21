package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;



@Configuration
public class DatabaseConfig {
    // from application.properties
    @Autowired
    Environment environment;
    private final String URL =      "spring.datasource.url";
    private final String USER =     "spring.datasource.username";
    private final String DRIVER =   "spring.datasource.driver-class-name";
    private final String PASSWORD = "spring.datasource.password";    
    @Bean
    DataSource dataSource() {
            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setUrl(environment.getProperty(URL));
            driverManagerDataSource.setUsername(environment.getProperty(USER));
            driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
            driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
            return driverManagerDataSource;
    }    
        
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }        
}
