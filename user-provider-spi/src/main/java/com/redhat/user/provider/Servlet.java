package com.redhat.user.provider;

import java.io.IOException;

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
		
		System.out.println(TokenManager.getInstance().getToken());
	}

}
