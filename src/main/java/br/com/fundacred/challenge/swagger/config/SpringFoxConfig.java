package br.com.fundacred.challenge.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author luisbsl
 *
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	private static final String TITLE = "Fundacred Challenge - Spring Boot API Restul";
	private static final String DESCRIPTION = "Cadastro, login e acesso ao perfil de usuários utilizando autenticação JWT";
	private static final String VERSION = "1.0.0";
	private static final Contact CONTACT = new Contact("Luis Ismael Lima", "https://github.com/luisbsl", "luisbsl@gmail.com");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.fundacred.challenge")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
						.license("MIT")
						.title(TITLE)
						.description(DESCRIPTION)
						.version(VERSION)
						.contact(CONTACT)
						.build();
	}
}