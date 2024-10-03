package org.example.multidatasourcetrainning.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceBeanConfig {
    @Primary
    @Bean(name = "datasource1Properties")
    @ConfigurationProperties("spring.datasource1")
    public DataSourceProperties dataSource1Properties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource dataSource1() {
        return dataSource1Properties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "datasource2Properties")
    @ConfigurationProperties("spring.datasource2")
    public DataSourceProperties dataSource2Properties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource2() {
        return dataSource2Properties().initializeDataSourceBuilder().build();
    }
}
