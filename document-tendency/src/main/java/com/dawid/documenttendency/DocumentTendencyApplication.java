package com.dawid.documenttendency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DocumentTendencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentTendencyApplication.class, args);
	}

}
