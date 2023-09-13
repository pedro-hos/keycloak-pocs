package org.keycloak.examples.domainextension.interceptor;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

public class TestExtension implements Extension {

	public void onProcessAnnotatedType(@Observes ProcessAnnotatedType<?> event) {
		System.out.println("onProcessAnnotatedType");
	}

	public void onAfterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
		System.out.println("onAfterDeploymentValidation");
	}

}
