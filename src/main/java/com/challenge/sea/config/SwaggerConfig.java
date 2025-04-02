package com.challenge.sea.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sea Challenge API")
                        .version("v1")
                        .description("API documentation for the Sea Challenge application")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
