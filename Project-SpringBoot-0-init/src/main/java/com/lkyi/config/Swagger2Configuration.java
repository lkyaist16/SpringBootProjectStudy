package com.lkyi.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;


/**
 * @author
 */
@EnableSwagger2
@Configuration
public class Swagger2Configuration {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lkyi.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .securitySchemes(newArrayList(apiKeySecurityScheme()))
                .securityContexts(newArrayList(apiKeySecurityContext()))
                .enableUrlTemplating(false)

                ;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        return new ApiInfo("init-service",
                "部分接口需要Token验证，可通过Authorize按钮输入Token",
                "1.0.0",
                "",
                contact,
                "",
                "",
                new ArrayList<>()
        );
    }

    private SecurityScheme apiKeySecurityScheme() {
        return new ApiKey("Token", "Authorization", "header");
    }

    private SecurityContext apiKeySecurityContext() {
        AuthorizationScope[] authScopes = new AuthorizationScope[1];
        authScopes[0] = new AuthorizationScopeBuilder()
                .scope("read")
                .description("access")
                .build();
        SecurityReference securityReference = SecurityReference.builder()
                .reference("Token")
                .scopes(authScopes)
                .build();

        return SecurityContext.builder().securityReferences(newArrayList(securityReference)).build();
    }


}