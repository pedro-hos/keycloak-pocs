package io.pedrohos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.spi.RegisterableService;
import javax.ws.rs.core.MultivaluedMap;

import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.events.EventType;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

/**
 * https://www.keycloak.org/docs/latest/server_development/#modifying-extending-the-registration-form
 * https://github.com/keycloak/keycloak/blob/master/services/src/main/java/org/keycloak/authentication/forms/RegistrationUserCreation.java
 * 
 * @author pesilva
 *
 */
public class MyFormAction implements FormAction {

	Logger logger = Logger.getLogger(MyFormAction.class.getName());

	@Override
	public void validate(ValidationContext context) {
		logger.info("validate()");

		MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
		List<FormMessage> errors = new ArrayList<>();

		context.getEvent().detail(Details.REGISTER_METHOD, "form");

		String username = formData.getFirst("username");
		context.getEvent().detail(Details.USERNAME, username);

		// Validating username with [Aa-Zz][0-9]_{6,20}
		if (!username.matches("\\w{6,20}")) {
			errors.add(new FormMessage("username", "username should be between 6 and 20 characters and _"));
		}

		if (errors.size() > 0) {
			context.error(Errors.INVALID_REGISTRATION);
			context.validationError(formData, errors);
			return;
		}

		context.success();
	}

	@Override
	public void success(FormContext context) {
		logger.info("success()");

		MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
		
		String email = formData.getFirst("email");
		String username = formData.getFirst("username");
		
		if (context.getRealm().isRegistrationEmailAsUsername()) {
			username = formData.getFirst("email");
		}
		
		context.getEvent().detail(Details.USERNAME, username)
						  .detail(Details.REGISTER_METHOD, "form")
						  .detail(Details.EMAIL, email);
		
		UserModel user = context.getSession().users().addUser(context.getRealm(), username);
		user.setEnabled(true);

		user.setEmail(email);
		context.getAuthenticationSession().setClientNote("login_hint", username);
		
		//AttributeFormDataProcessor.process(formData, context.getRealm(), user);
		
		context.setUser(user);
		context.getEvent().user(user);
		context.getEvent().success();
		context.newEvent().event(EventType.LOGIN);
		context.getEvent().client(context.getAuthenticationSession().getClient().getClientId())
				.detail(Details.REDIRECT_URI, context.getAuthenticationSession().getRedirectUri())
				.detail(Details.AUTH_METHOD, context.getAuthenticationSession().getProtocol());
		
		String authType = context.getAuthenticationSession().getAuthNote(Details.AUTH_TYPE);
		
		if (authType != null) {
			context.getEvent().detail(Details.AUTH_TYPE, authType);
		}
	}

	@Override
	public void close() { }

	@Override
	public void buildPage(FormContext context, LoginFormsProvider loginFormsProvider) { }

	@Override
	public boolean configuredFor(KeycloakSession arg0, RealmModel arg1, UserModel arg2) {
		return true;
	}

	@Override
	public boolean requiresUser() {
		return false;
	}

	@Override
	public void setRequiredActions(KeycloakSession arg0, RealmModel arg1, UserModel arg2) { }

}
