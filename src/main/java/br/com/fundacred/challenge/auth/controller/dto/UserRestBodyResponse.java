package br.com.fundacred.challenge.auth.controller.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRestBodyResponse extends RestBodyResponse {

	private String id;
	private Date created;
	private Date modified;
	private Date lastLogin;
	private String token;

}
