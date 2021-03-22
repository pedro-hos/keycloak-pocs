package com.redhat.user.provider;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Controller {
	
	public Controller() throws NamingException {
		 InitialContext ctx = new InitialContext();
		 TokenManager provider = (TokenManager)ctx.lookup("java:global/user-provider-spi/TokenManager");
		 System.out.println(provider.getToken());
	}
		
}