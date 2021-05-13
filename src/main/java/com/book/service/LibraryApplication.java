package com.book.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.google.common.base.Predicates.not;

@SpringBootApplication
@EnableSwagger2
public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Value("${swagger.api.version}")
    String swaggerApiVersion;

    @Bean
    public Docket swaggerApiDocumentation(){
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .groupName("Api")
                .ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class, ApiIgnore.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.book.service"))
                .build()
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Library Manager Microservice API Documentation")
                .description("<p>Endpoints for managing existing reservations.</p>")
                .version(swaggerApiVersion)
                .build();
    }

    @Bean
    public Docket swaggerInternalDocumentation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Internal")
                .select()
                .apis(not(RequestHandlerSelectors.basePackage("com.book.service")))
                .build()
                .pathMapping("/library")
                .tags(new Tag("Status endpoints", "All endpoints relating to the status of the Microservice"));
    }
}
