package com.dawid.documentpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DocumentPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentPublisherApplication.class, args);
	}

}
