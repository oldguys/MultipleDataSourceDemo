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
@EnableJpaRepositories(entityManagerFactoryRef = "test4EntityManagerFactory",
        basePackages = "com.oldguy.example.modules.test4.dao.jpas",
        transactionManagerRef = "test4TransactionManager")
public class Test4DataSourceConfiguration extends AbstractJpaConfiguration{


    @Value("${test4.jpa.base-package}")
    private String entitiesPackage;

    @Bean(name = "test4DataSource")
    @ConfigurationProperties(prefix = "test4.datasource")
    public DataSource test4DataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test4JpaProperties")
    @ConfigurationProperties(prefix = "test4.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }


    @Bean(name = "test4JpaVendorAdapter")
    public JpaVendorAdapter createJpaVendorAdapter(@Qualifier("test4JpaProperties") JpaProperties properties, @Qualifier("test4DataSource") DataSource dataSource) {

        return super.createJpaVendorAdapter(properties, dataSource);
    }


    @Bean(name = "test4EntityManagerFactory")
    public EntityManagerFactory LocalContainerEntityManagerFactoryBean(
            @Qualifier("test4DataSource") DataSource dataSource,
            @Qualifier("test4JpaProperties") JpaProperties jpaProperties,
            @Qualifier("test4JpaVendorAdapter") JpaVendorAdapter jpaVendorAdapter) {


        return super.createEntityManagerFactory(dataSource, jpaProperties, jpaVendorAdapter, entitiesPackage);
    }


    @Bean(name = "test4TransactionManager")
    public JpaTransactionManager createTransactionManager(@Qualifier("test4EntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return super.createTransactionManager(entityManagerFactory);
    }

}
