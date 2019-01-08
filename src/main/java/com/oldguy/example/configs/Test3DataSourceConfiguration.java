package com.oldguy.example.configs;

import com.alibaba.druid.pool.DruidDataSource;
import com.oldguy.example.modules.common.configs.AbstractJpaConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "test3EntityManagerFactory",
        basePackages = "com.oldguy.example.modules.test3.dao.jpas",
        transactionManagerRef = "test3TransactionManager")
public class Test3DataSourceConfiguration extends AbstractJpaConfiguration {

    @Value("${test3.jpa.base-package}")
    private String entitiesPackage;

    @Bean(name = "test3DataSource")
    @ConfigurationProperties(prefix = "test3.datasource")
    public DataSource test3DataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test3JpaProperties")
    @ConfigurationProperties(prefix = "test3.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }


    @Bean(name = "test3JpaVendorAdapter")
    public JpaVendorAdapter createJpaVendorAdapter(@Qualifier("test3JpaProperties") JpaProperties properties, @Qualifier("test3DataSource") DataSource dataSource) {

        return super.createJpaVendorAdapter(properties, dataSource);
    }


    @Bean(name = "test3EntityManagerFactory")
    public EntityManagerFactory LocalContainerEntityManagerFactoryBean(
            @Qualifier("test3DataSource") DataSource dataSource,
            @Qualifier("test3JpaProperties") JpaProperties jpaProperties,
            @Qualifier("test3JpaVendorAdapter") JpaVendorAdapter jpaVendorAdapter) {


        return super.createEntityManagerFactory(dataSource, jpaProperties, jpaVendorAdapter, entitiesPackage);
    }


    @Bean(name = "test3TransactionManager")
    public JpaTransactionManager createTransactionManager(@Qualifier("test3EntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return super.createTransactionManager(entityManagerFactory);
    }


}
