#SpringBoot 配置多数据源 MyBatis

>在 SpringBoot 项目中，可能需要使用 MyBatis 对不同数据库进行操作，而SpringBoot默认配置只是使用单数据源。
>本文主要描述如何配置 SpringBoot 多数据源 MyBatis。
> GitHub  [https://github.com/oldguys/MultipleDataSourceDemo](https://github.com/oldguys/MultipleDataSourceDemo)


Step1: 排除自动装配。
            SpringBoot会自动扫描配置数据源，需要先排除自动装配，防止报找不到多重实例，找不到准确引用。
```
package com.oldguy.example;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {

DataSourceAutoConfiguration.class,

      MybatisAutoConfiguration.class,

})

public class MultipleDatasourceDemoApplication {

public static void main(String[] args) {

SpringApplication.run(MultipleDatasourceDemoApplication.class, args);

  }

}
```

Step2: 从SpringBoot中自动装配代码中，取出需要的部分。
```
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

```
Step3: 实现数据模板

配置实例模块 Test1

```
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
```

配置实例模块 Test2
```
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

```
Step4：配置 yaml文件

```
test1:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3306/multiple_datasource1?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
  mybatis:
    mapper-locations: classpath:mappers/test1/*.xml
    type-aliases-package: com.oldguy.example.modules.test1.dao.entities;
    config-location: classpath:configs/myBatis-config.xml

test2:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3306/multiple_datasource2?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
  mybatis:
    mapper-locations: classpath:mappers/test2/*.xml
    type-aliases-package: com.oldguy.example.modules.test2.dao.entities;
    config-location: classpath:configs/myBatis-config.xml

```

到此完成了MyBatis多数据源配置
代码可以参考   GitHub  [https://github.com/oldguys/MultipleDataSourceDemo](https://github.com/oldguys/MultipleDataSourceDemo)