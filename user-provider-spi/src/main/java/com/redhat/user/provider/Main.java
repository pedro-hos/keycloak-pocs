package com.redhat.user.provider;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {

	public static void main(String[] args) throws NamingException {
		InitialContext ctx = new InitialContext();
		 TokenManager provider = (TokenManager)ctx.lookup("java:global/user-provider-spi/" + TokenManager.class.getCanonicalName());
		 System.out.println(provider.getToken());
	}

}
