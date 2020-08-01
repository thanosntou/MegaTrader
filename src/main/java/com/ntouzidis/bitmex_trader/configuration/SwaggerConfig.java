package com.ntouzidis.bitmex_trader.configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(Predicates.not(PathSelectors.ant("/error/**")))
            .paths(Predicates.not(PathSelectors.ant("/oauth/error/**")))
            .paths(Predicates.not(PathSelectors.ant("/oauth/authorize/**")))
            .paths(Predicates.not(PathSelectors.ant("/oauth/check_token/**")))
            .paths(Predicates.not(PathSelectors.ant("/oauth/confirm_access/**")))
            .build();
  }
}
