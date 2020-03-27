package br.com.fundacred.challenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HelloWorld {
	
	@GetMapping("/hello")
	String hello() {
		return "Hello World";
	}

}
