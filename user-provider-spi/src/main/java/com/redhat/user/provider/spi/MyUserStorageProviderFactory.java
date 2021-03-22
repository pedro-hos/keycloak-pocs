/**
 * 
 */
package com.redhat.user.provider.spi;

import javax.naming.InitialContext;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

/**
 * @author pedro-hos@outlook.com
 *
 */
public class MyUserStorageProviderFactory implements UserStorageProviderFactory<MyUserStorageProvider>{

	private static final Logger logger = Logger.getLogger(MyUserStorageProviderFactory.class);
	
	@Override
	public MyUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		
		logger.info("starting ...");
		
		 try {
	            InitialContext ctx = new InitialContext();
	            MyUserStorageProvider provider = (MyUserStorageProvider)ctx.lookup("java:global/user-provider-spi/MyUserStorageProvider");
	            return provider;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		 
	}

	@Override
	public String getId() {
		 return "example-user-storage-jpa";
	}
	
	@Override
    public String getHelpText() {
        return "JPA Example User Storage Provider";
    }

    @Override
    public void close() {
        logger.info("<<<<<< Closing factory");

    }

}
