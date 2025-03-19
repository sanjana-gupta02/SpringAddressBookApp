package com.spring.AddressBook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;  // Corrected import

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springbook-public")
                .packagesToScan("com.spring.AddressBook")
                .build();
    }

    @Bean
    public Info apiInfo() {
        return new Info()
                .title("Address Book API")
                .description("API documentation for Address Book Application")
                .version("1.0")
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"));
    }
}
