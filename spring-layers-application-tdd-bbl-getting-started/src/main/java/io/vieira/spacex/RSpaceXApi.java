package io.vieira.spacex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSpaceXConfiguration.class)
public class RSpaceXApi {

    public static void main(String[] args) {
        SpringApplication.run(RSpaceXApi.class, args);
    }
}
