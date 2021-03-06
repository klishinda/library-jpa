package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@EnableMongoRepositories
@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@EnableHystrix
@EnableHystrixDashboard
//@EnableConfigServer
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}