package com.tcs.training.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class APIGatewayService {

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayService.class, args);
    }

}
