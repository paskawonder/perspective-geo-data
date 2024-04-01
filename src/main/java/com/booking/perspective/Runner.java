package com.booking.perspective;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
@Configuration
public class Runner {
    
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
    
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/geo").setViewName("forward:/geo/geo.html");
            }
        };
    }
    
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(@Value("${secrets.aws.credentials.file-path}") String path) {
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DynamoDbClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.builder()
                    .profileFile(ProfileFile.builder().content(Path.of(path)).type(ProfileFile.Type.CREDENTIALS).build()).build()
                ).region(Region.EU_WEST_2).build()
            ).build();
    }

}
