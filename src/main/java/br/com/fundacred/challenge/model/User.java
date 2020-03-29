package br.com.fundacred.challenge.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author luisbsl
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@NonNull
	private String token;

	@NotBlank(message = "Campo nome obrigatório")
	@NonNull
	private String name;

	@Column(unique = true)
	@NotBlank(message = "Campo email obrigatório")
	@Email(message = "Email inválido")
	@NonNull
	private String email;

	@NotBlank(message = "Campo Senha orbigatório")
	@NonNull
	private String password;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Phone> phones = new HashSet<>();

	@Column(updatable = false)
	private Date created;
	private Date modified;
	private Date lastLogin;

}
