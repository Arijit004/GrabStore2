package com.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//declaring this application as a eureka server so other microservices can be stored as eureka clients in eureka server...
//start ServiceRegistryApplication before running any other registered service (e.g. QuizApplication) because each and every service is registered in Eureka Server (e.g. ServiceRegistry)....
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
