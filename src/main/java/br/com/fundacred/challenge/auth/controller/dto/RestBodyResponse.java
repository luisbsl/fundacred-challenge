package br.com.fundacred.challenge.auth.controller.dto;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RestBodyResponse {

	private Set<String> mensagens;
	private HttpStatus status;

}
