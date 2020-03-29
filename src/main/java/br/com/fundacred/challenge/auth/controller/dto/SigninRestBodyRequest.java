package br.com.fundacred.challenge.auth.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author luisbsl
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SigninRestBodyRequest implements RestBodyRequest {

	@NotBlank(message = "Campo email obrigatório")
	@Email(message = "Campo email inválido")
	private String email;

	@NotBlank(message = "Campo senha obrigatório")
	private String password;

}
