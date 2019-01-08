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
