package br.com.fundacred.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HelloWorld {
	
	@Autowired
	private Environment env;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping("/hello")
	String hello() {
		return "Hello World - "+appName+" - "+env.getProperty("SPRING_DATASOURCE_URL");
	}

}
