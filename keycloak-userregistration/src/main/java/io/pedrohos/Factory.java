package io.pedrohos;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class Factory implements UserStorageProviderFactory<MyUserStorageProvider> {

	@Override
	public MyUserStorageProvider create(KeycloakSession arg0, ComponentModel arg1) {
		System.out.println(">>>> create");
		return new MyUserStorageProvider();
	}

	@Override
	public String getId() {
		System.out.println(">>>> getId");
		return "id";
	}


}
