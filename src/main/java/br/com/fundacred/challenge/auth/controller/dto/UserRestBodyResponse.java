package br.com.fundacred.challenge.auth.controller.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author luisbsl
 *
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRestBodyResponse extends RestBodyResponse {

	private String id;
	private Date created;
	private Date modified;
	@JsonProperty("last_login")
	private Date lastLogin;
	private String token;

}
