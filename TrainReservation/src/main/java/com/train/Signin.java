package com.train;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * Servlet implementation class Signin
 */
public class Signin extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger log=Logger.getLogger(Signin.class);
		 String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/TicketResevation/src/main/java/log4j/log4j.properties";
		 PropertyConfigurator.configure(log4jConfPath);
		String email =request.getParameter("email");
		String password = request.getParameter("pass");
		
		try {
			boolean condition=Landing.signinCheck(email,password);
			System.out.println(condition);
			if(condition) {
				log.info("succesfully pass the condition");
			      response.sendRedirect("TrainRegis.html");
						}
			
		} catch (NoSuchAlgorithmException | SQLException e) {
		       log.debug("cookie is setted");
		} catch (ClassNotFoundException e) {
			   log.debug("class not found");
		}
	}

}
