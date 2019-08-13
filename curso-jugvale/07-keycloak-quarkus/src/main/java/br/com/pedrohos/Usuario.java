package br.com.pedrohos;

import org.keycloak.KeycloakSecurityContext;

public class Usuario {

	private final String nome;

	public Usuario(KeycloakSecurityContext securityContext) {
		this.nome = securityContext.getToken().getPreferredUsername();
	}

	public String getNome() {
		return nome;
	}
}
