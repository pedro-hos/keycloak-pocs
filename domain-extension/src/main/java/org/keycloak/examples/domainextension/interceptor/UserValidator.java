package org.keycloak.examples.domainextension.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@UserValidate
public class UserValidator {

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object auditar(InvocationContext context) throws Exception {
		logger.info("Hello!");
		System.out.println("BLA");
		Method method = context.getMethod();
		Object target = context.getTarget();
		Object[] params = context.getParameters();
		logger.info(String.format("auditando o método: '%s' " + "do objeto: '%s' " + "com os parâmetros: '%s'", method, target, params));
		Object retorno = context.proceed();
		return retorno;
	}
}
