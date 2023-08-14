package com.javatechie.keycloak;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableKafka
@ComponentScan(basePackages = {"com.javatechie.keycloak", "com.javatechie.keycloak.converter"})
public class SpringBootKeycloakExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKeycloakExampleApplication.class, args);
    }
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
