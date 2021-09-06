package com.pedrohos.example.keycloak.servletsecure;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */


@WebServlet("/secured")
@ServletSecurity(httpMethodConstraints = { @HttpMethodConstraint(value = "GET", rolesAllowed = { "Users" }) })
public class SecuredServlet extends HttpServlet {

	private static final long serialVersionUID = 2406672965028327335L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter writer = resp.getWriter()) {
			writer.println("<html>");
			writer.println("<head><title>Secured Servlet</title></head>");
			writer.println("<body>");
			writer.println("<h1>Secured Servlet</h1>");
			writer.println("<p>");
			writer.print(" Current Principal '");
			Principal user = req.getUserPrincipal();
			writer.print(user != null ? user.getName() : "NO AUTHENTICATED USER");
			writer.print("'");
			writer.println("    </p>");
			writer.println("  </body>");
			writer.println("</html>");
		}
	}
}
