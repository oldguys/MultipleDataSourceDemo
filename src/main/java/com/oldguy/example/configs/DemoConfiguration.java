package com.oldguy.example.configs;

import com.oldguy.example.modules.common.utils.Log4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author ren
 * @date 2018/12/20
 */
@Configuration
public class DemoConfiguration {

    @Resource(name = "test1DataSource")
    private DataSource test1dataSource;

    @Value("${test1.mybatis.type-aliases-package}")
    private String test1TypeAliasesPackage;

    @Resource(name = "test2DataSource")
    private DataSource test2dataSource;
    @Value("${test2.mybatis.type-aliases-package}")
    private String test2TypeAliasesPackage;



    @PostConstruct
    public void initData() throws IOException {

        Log4jUtils.getInstance(getClass()).info("初始化 数据库 -------------------------------");
        DbRegisterConfiguration dbConfiguration = new DbRegisterConfiguration();

        // 自动生成表结构 test1
        JdbcTemplate jdbcTemplate = new JdbcTemplate(test1dataSource);
        dbConfiguration.initDB(jdbcTemplate,test1TypeAliasesPackage);

        // 自动生成表结构 test2
        jdbcTemplate = new JdbcTemplate(test2dataSource);
        dbConfiguration.initDB(jdbcTemplate,test2TypeAliasesPackage);

    }
}
