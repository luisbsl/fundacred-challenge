package br.com.fundacred.challenge.auth.controller.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
public class SignupRestBodyRequest implements RestBodyRequest {

	@NotBlank(message = "Campo nome obrigatório")
	private String name;

	@NotBlank(message = "Campo email obrigatório")
	@Email(message = "Campo email inválido")
	private String email;

	@NotBlank(message = "Campo senha obrigatório")
	private String password;

	@Valid
	@NotEmpty(message = "Obrigatório ao menos um telefone")
	private Set<PhoneRestBodyRequest> phones;

}
