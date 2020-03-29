package br.com.fundacred.challenge.auth.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoneRestBodyRequest implements RestBodyRequest {

	@NotBlank(message = "Campo ddd obrigatório")
	@Pattern(regexp = "(\\d{2})", message = "Campo ddd inválido")
	private String ddd;

	@NotBlank(message = "Campo ddd obrigatório")
	@Pattern(regexp = "^(9)(\\d{8})", message = "Campo número inválido")
	private String number;

}
