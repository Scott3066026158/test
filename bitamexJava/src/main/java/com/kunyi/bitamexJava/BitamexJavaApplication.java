package com.kunyi.bitamexJava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(value = "com.kunyi.bitamexJava.mapper")
@SpringBootApplication
public class BitamexJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitamexJavaApplication.class, args);
	}

}
