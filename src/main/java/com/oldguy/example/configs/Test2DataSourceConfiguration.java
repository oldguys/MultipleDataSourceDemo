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
@MapperScan(basePackages = "com.oldguy.example.modules.test2.dao.jpas",
        sqlSessionFactoryRef = "test2SqlSessionFactory",
        sqlSessionTemplateRef = "test2SqlSessionTemplate"
)
public class Test2DataSourceConfiguration extends AbstractMybatisConfiguration {

    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "test2.datasource")
    public DruidDataSource test2DataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test2Properties")
    @ConfigurationProperties(prefix = "test2.mybatis")
    public MybatisProperties MybatisProperties() {
        return new MybatisProperties();
    }

    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("test2DataSource") DruidDataSource dataSource,
                                                   @Qualifier("test2Properties") MybatisProperties properties,
                                                   ResourceLoader resourceLoader) throws Exception {
        return getSqlSessionFactoryBean(dataSource, properties, resourceLoader).getObject();
    }

    @Bean(name = "test2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test1Properties") MybatisProperties properties,
                                                 @Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(properties, sqlSessionFactory);
    }

}
