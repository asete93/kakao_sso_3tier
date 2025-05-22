package com.camel.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.camel.api.services.camel.repository",
        entityManagerFactoryRef = "camelEntityManagerFactory",
        transactionManagerRef = "camelTransactionManager"
)
public class CamelDatasourceConfig {
    @Bean(name = "camelDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.camel")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "camelEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("camelDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.camel.api.services.camel.dao")
                .persistenceUnit("camel")
                .build();
    }

    @Bean(name = "camelTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("camelEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
