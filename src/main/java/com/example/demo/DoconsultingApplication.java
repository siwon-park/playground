package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 일단은 DB를 연결하지 않는 설정으로 사용함
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DoconsultingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoconsultingApplication.class, args);
	}

}
