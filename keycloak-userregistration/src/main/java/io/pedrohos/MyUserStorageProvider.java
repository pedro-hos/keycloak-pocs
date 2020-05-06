package io.pedrohos;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

public class MyUserStorageProvider implements UserStorageProvider, UserRegistrationProvider {

	@Override
	public UserModel addUser(RealmModel realmModel, String username) {
		
		System.out.println(">>>> addUser");
				
		return null;
	}

	@Override
	public boolean removeUser(RealmModel realmModel, UserModel userModel) {
		System.out.println(">>>> removeUser");
		return false;
	}
	
	@Override
	public void close() { 
		System.out.println(">>>> close");
	}

}
