/**
 * 
 */
package com.redhat.user.provider.spi;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.logging.Logger;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import com.redhat.user.provider.TokenManager;
/**
 * @author pedro-hos@outlook.com
 *
 */
@Stateful
@Local(MyUserStorageProvider.class)
public class MyUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

	private static final Logger logger = Logger.getLogger(MyUserStorageProvider.class);
	
	@PostConstruct
	private void init() {
		
		try {
			logger.info("finding token ...");
			logger.info("Token" + TokenManager.getInstance().getToken());
			
		} catch (Exception e) {
			logger.error("Fail");
			e.printStackTrace();
		}
	}
    
    @Remove
	@Override
	public void close() {
    	logger.info("close()");
		
	}

	@Override
	public boolean isConfiguredFor(RealmModel arg0, UserModel arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(RealmModel arg0, UserModel arg1, CredentialInput arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsCredentialType(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserModel getUserByEmail(String arg0, RealmModel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserById(String arg0, RealmModel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserByUsername(String arg0, RealmModel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
