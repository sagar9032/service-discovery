package com.discover.userservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI userOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
				.addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1")).addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema())))
				.info(new Info().title("User API").description("User application").version("v1").termsOfService("http://user.io/terms/")
						.contact(new Contact().name("API Support")
								/*.email("sagar.kurapati@capgemini.com").url("http://example.com/support")*/
						)
				.license(new License().name("Discover 2.0").url("http://userdoc.org")))
				//.addTagsItem(new Tag().name("myTag"))
				.externalDocs(new ExternalDocumentation().description("User Wiki Documentation").url("https://user.wiki.github.org/docs"));
	}

	@Bean
	public GroupedOpenApi userGroupedOpenApi() {
		String[] paths = {"/users/**"};
		return GroupedOpenApi.builder().group("users").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi userGroupedRestOpenApi() {
		String[] paths = {"/rest/user/tickets/**"};
		return GroupedOpenApi.builder().group("tickets").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi userGroupedFeignOpenApi() {
		String[] paths = {"/feign/user/tickets/**"};
		return GroupedOpenApi.builder().group("feign").pathsToMatch(paths).build();
	}

}