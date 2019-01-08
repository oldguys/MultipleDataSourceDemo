#SpringBoot 配置多数据源 JPA


>之前则介绍了怎样从SpringBoot源码中，摘出配置MyBatis的方法。这次，则开始摘出Jpa的配置方法。
>GitHub [https://github.com/oldguys/MultipleDataSourceDemo](https://github.com/oldguys/MultipleDataSourceDemo)

Step1: 剔除自动配置类
```
package com.oldguy.example;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
	//	MybatisAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
})
public class MultipleDatasourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatasourceDemoApplication.class, args);
	}

}


```
Step2: 从SpringBoot 源码中，摘出Jpa部分配置代码，并编写成为配置类
```
package com.oldguy.example.modules.common.configs;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
public abstract class AbstractJpaConfiguration {


    protected JpaVendorAdapter createJpaVendorAdapter(JpaProperties properties, DataSource dataSource) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(properties.isShowSql());
        adapter.setDatabase(properties.determineDatabase(dataSource));
        adapter.setDatabasePlatform(properties.getDatabasePlatform());
        adapter.setGenerateDdl(properties.isGenerateDdl());
        return adapter;
    }


    protected EntityManagerFactory createEntityManagerFactory(
            DataSource dataSource,
            JpaProperties jpaProperties,
            JpaVendorAdapter jpaVendorAdapter,
            String entitiesPackage) {

        Map<String, String> map = jpaProperties.getHibernateProperties(dataSource);

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan(entitiesPackage);
        bean.setJpaPropertyMap(map);


        // 进行配置
        bean.afterPropertiesSet();

        return bean.getObject();
    }


    protected JpaTransactionManager createTransactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }
}

```
Step3: 编写实现类

1.  数据源3
```
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

```
2.  数据源4
```
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

```
Step4:编写配置文件
```

test3:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    base-package: com.oldguy.example.modules.test3.dao.entities
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/multiple_datasource3?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    type: org.apache.tomcat.jdbc.pool.DataSource

test4:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    base-package: com.oldguy.example.modules.test4.dao.entities
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/multiple_datasource4?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    type: org.apache.tomcat.jdbc.pool.DataSource
```

到此就完成了多重数据源配置。

MyBatis 测试结果：
```
package com.oldguy.example.modules.controllers;

import com.oldguy.example.modules.test1.dao.jpas.TestEntity1Mapper;
import com.oldguy.example.modules.test2.dao.jpas.TestEntity2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
@RestController
public class TestController {

    /**
     *  MyBatis 数据库2
     */
    @Autowired
    private TestEntity2Mapper testEntity2Mapper;
    /**
     *  MyBatis 数据库1
     */
    @Autowired
    private TestEntity1Mapper testEntity1Mapper;

    @GetMapping("test1")
    public Object test1(){
        return testEntity1Mapper.findAllByStatus(null);
    }

    @GetMapping("test2")
    public Object test2(){
        return testEntity2Mapper.findAllByStatus(null);
    }
}
```
图片结果：
![1.png](https://upload-images.jianshu.io/upload_images/14387783-08715535aba41a81.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![2.png](https://upload-images.jianshu.io/upload_images/14387783-2319e007785fd08b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Jpa 测试结果：
1.  数据源3
```
package com.oldguy.example.modules.test3.controllers;

import com.oldguy.example.modules.test3.dao.jpas.TestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@RestController
@RequestMapping("TestUser")
public class TestUserController {

    @Autowired
    private TestUserRepository testUserRepository;


    @GetMapping("list")
    public Object getList(){
        return testUserRepository.findAll();
    }
}

```

2.  数据源4
```
package com.oldguy.example.modules.test4.controllers;

import com.oldguy.example.modules.test4.dao.jpas.RemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@RestController
@RequestMapping("Test4")
public class Test4Controller {


    @Autowired
    private RemarkRepository remarkRepository;

    @GetMapping("message")
    public Object getMessage(String message){
        return remarkRepository.findByMessage(message);
    }

    @GetMapping("list")
    public Object getList(){
        return remarkRepository.findAll();
    }
}

```
图片结果：
![3.png](https://upload-images.jianshu.io/upload_images/14387783-5c1206365aa85171.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![4.png](https://upload-images.jianshu.io/upload_images/14387783-83ab60cd927e8596.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

以上就完成了SpringBoot 同时使用MyBatis 和 Jpa 配置双方多重数据源。
代码可以参考 GitHub [https://github.com/oldguys/MultipleDataSourceDemo](https://github.com/oldguys/MultipleDataSourceDemo)
