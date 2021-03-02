package com.numbergenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.numbergenerator.*")
public class NumberGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberGeneratorApplication.class, args);
	}

}
