package com.example.FintechBackendDeveloperAssignment.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import java.util.Arrays;
import java.util.Collections;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Umair && Yousaf");
        myContact.setEmail("your.email@gmail.com");

        Info information = new Info()
                .title("Fintech Backend Developer Assignment")
                .version("1.0")
                .description("This API exposes endpoints to manage Fintech users and paymnet made by umair and Yousaf.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(Collections.unmodifiableList(Arrays.asList(server)));
    }
}