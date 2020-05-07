package io.pedrohos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

public class MyFormActionFactory implements FormActionFactory, ServerInfoAwareProviderFactory {
	
	Logger logger = Logger.getLogger(MyFormActionFactory.class.getName());
	 
	public static final String PROVIDER_ID = "registration-user-creation";
	 
	@Override
	public void close() { }

	@Override
	public FormAction create(KeycloakSession arg0) {
		return new MyFormAction();
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public void init(Scope arg0) { }

	@Override
	public void postInit(KeycloakSessionFactory arg0) { }

	@Override
	public String getDisplayType() {
		return "Registration User Creation";
	}

	@Override
	public String getReferenceCategory() {
		return null;
	}

	@Override
	public boolean isConfigurable() {
		return false;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return null;
	}

	@Override
	public boolean isUserSetupAllowed() {
		return false;
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return null;
	}

	@Override
	public String getHelpText() {
        return "This action must always be first! Validates the username of the user in validation phase.  In success phase, this will create the user in the database.";
	}

	@Override
	public Map<String, String> getOperationalInfo() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("version", "1");
		return map;
	}
	

}
