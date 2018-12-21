package com.oldguy.example.modules.common.configs;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author ren
 * @date 2018/12/20
 */
public abstract class AbstractMybatisConfiguration {

    /**
     *  获取Template
     * @param properties
     * @param sqlSessionFactory
     * @return
     */
    protected SqlSessionTemplate getSqlSessionTemplate(MybatisProperties properties, SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    /**
     *  获取 SessionFactory
     * @param dataSource
     * @param properties
     * @param resourceLoader
     * @return
     */
    protected SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource, MybatisProperties
            properties, ResourceLoader resourceLoader) {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);

        factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));

        org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }

        factory.setConfiguration(configuration);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }

        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }
        return factory;
    }

}
