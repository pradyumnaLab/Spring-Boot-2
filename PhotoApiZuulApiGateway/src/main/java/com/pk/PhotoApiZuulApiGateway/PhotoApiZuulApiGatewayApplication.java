package com.pk.PhotoApiZuulApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class PhotoApiZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoApiZuulApiGatewayApplication.class, args);
	}

}
