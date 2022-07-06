package pl.sda.arppl4.spring_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringfoxConfig {
    @Bean
    public Docket apiDocket() {             // Docket zawiera informacje o api
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("Moje Pierwsze API", "Moje pierwsze A. P. I.",
                        "0.99", null, new Contact("Paweł", null, "pawel.reclaw@gmail.com"),
                        null, null, new ArrayList<>()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.sda.arppl4.spring_demo.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();

        // dostępne jest pod stroną: http://localhost:8080/swagger-ui/index.html#
    }
}
