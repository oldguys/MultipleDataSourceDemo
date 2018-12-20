package com.oldguy.example.configs;

import com.alibaba.druid.pool.DruidDataSource;
import com.oldguy.example.modules.common.configs.AbstractMybatisConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author ren
 * @date 2018/12/20
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.oldguy.example.modules.test1.dao.jpas",
        sqlSessionFactoryRef = "test1SqlSessionFactory",
        sqlSessionTemplateRef = "test1SqlSessionTemplate"
)
public class Test1DataSourceConfiguration extends AbstractMybatisConfiguration {

    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "test1.datasource")
    public DruidDataSource test1DataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test1Properties")
    @ConfigurationProperties(prefix = "test1.mybatis")
    public MybatisProperties MybatisProperties() {
        return new MybatisProperties();
    }

    @Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("test1DataSource") DruidDataSource dataSource,
                                                   @Qualifier("test1Properties") MybatisProperties properties,
                                                   ResourceLoader resourceLoader) throws Exception {
        return getSqlSessionFactoryBean(dataSource, properties, resourceLoader).getObject();
    }

    @Bean(name = "test1SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test1Properties") MybatisProperties properties,
                                                 @Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(properties, sqlSessionFactory);
    }

}
