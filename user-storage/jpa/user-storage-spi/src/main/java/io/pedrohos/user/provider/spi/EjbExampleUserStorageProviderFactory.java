package io.pedrohos.user.provider.spi;

import javax.naming.InitialContext;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class EjbExampleUserStorageProviderFactory implements UserStorageProviderFactory<EjbExampleUserStorageProvider> {
	
    private static final Logger logger = Logger.getLogger(EjbExampleUserStorageProviderFactory.class);

    @Override
    public EjbExampleUserStorageProvider create(KeycloakSession session, ComponentModel model) {

        try {
            InitialContext ctx = new InitialContext();
            EjbExampleUserStorageProvider provider = (EjbExampleUserStorageProvider)ctx.lookup("java:global/user-storage-spi/" + EjbExampleUserStorageProvider.class.getSimpleName());
            provider.setModel(model);
            provider.setSession(session);
            return provider;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getId() {
        return "user-storage-spi";
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
