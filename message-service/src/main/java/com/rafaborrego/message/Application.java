package com.rafaborrego.message;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.rafaborrego.message.client"})
public class Application {

	public static void main(String[] args) {

		new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
				.run(args);
	}
}
