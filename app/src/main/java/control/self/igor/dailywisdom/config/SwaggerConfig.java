package control.self.igor.dailywisdom.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.basePackage("control.self.igor.dailywisdom")).paths(PathSelectors.any())
		.build().apiInfo(apiInfo()).securitySchemes(Lists.newArrayList(apiKey()));

    }

    @Bean
    public SecurityConfiguration securityInfo() {
	return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
    }

    private ApiKey apiKey() {
	return new ApiKey("Authorization", "Authorization", "header");
    }

    private ApiInfo apiInfo() {
	ApiInfo apiInfo = new ApiInfo("Daily Wisdom REST API", "REST API for both data management and it's source",
		"1.0", "Terms of service",
		new Contact("Igor Roztropiński", "http://www.iprogrammerr.com", "ceigor94@gmail.com"), "", "",
		new ArrayList<>());
	return apiInfo;
    }

}
