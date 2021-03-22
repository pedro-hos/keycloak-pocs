package com.redhat.user.provider;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ControllerServlet", urlPatterns = "/controllerServlet")
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = 4697650595498129929L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			TokenManager provider = (TokenManager) ctx.lookup("java:global/user-provider-spi/TokenManager");
			System.out.println(provider.getToken());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
