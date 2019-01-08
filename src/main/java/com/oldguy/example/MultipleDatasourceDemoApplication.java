package com.oldguy.example;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		MybatisAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
})
public class MultipleDatasourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatasourceDemoApplication.class, args);
	}

}

