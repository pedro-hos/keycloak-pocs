package io.pedrohos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.keycloak.OAuthErrorException;
import org.keycloak.adapters.ServerRequest.HttpFailure;
import org.keycloak.adapters.installed.KeycloakInstalled;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException, VerificationException, OAuthErrorException, URISyntaxException, HttpFailure, InterruptedException {

		KeycloakInstalled keycloak = new KeycloakInstalled();
		keycloak.setLocale(Locale.ENGLISH);
		keycloak.loginDesktop();

		AccessToken token = keycloak.getToken();

		Executors.newSingleThreadExecutor().submit(() -> {

			System.out.println("Logged in...");
			System.out.println("Token: " + token.getSubject());
			System.out.println("Username: " + token.getPreferredUsername());
			
			try {
				System.out.println("AccessToken: " + keycloak.getTokenString());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			int timeoutSeconds = 20;
			System.out.printf("Logging out in...%d Seconds%n", timeoutSeconds);
			try {
				TimeUnit.SECONDS.sleep(timeoutSeconds);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				keycloak.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Exiting...");
			System.exit(0);
		});
	}
}
