package org.example.multidatasourcetrainning.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.example.multidatasourcetrainning.db1",
        entityManagerFactoryRef = "entityManagerFactory1",
        transactionManagerRef = "transactionManager1"
)
public class Datasource1Config {
    @Autowired
    private DataSource dataSource1;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource1)
                .packages("org.example.multidatasourcetrainning.db1")
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager1(EntityManagerFactory entityManagerFactory1) {
        return new JpaTransactionManager(entityManagerFactory1);
    }
}
