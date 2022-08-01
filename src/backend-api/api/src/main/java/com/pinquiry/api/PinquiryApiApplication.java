package com.pinquiry.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.pinquiry.api.model"})
public class PinquiryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinquiryApiApplication.class, args);
	}


}
