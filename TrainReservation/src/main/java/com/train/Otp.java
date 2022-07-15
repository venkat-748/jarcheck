package com.train;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.catalina.valves.rewrite.Substitution.StaticElement;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Otp
 */
@WebServlet("/Otp")
public class Otp extends HttpServlet {
	static int  i;
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		for(int i=0;i<3;i++) {
	i=Integer.parseInt(request.getParameter("otp"));
	boolean b =true;
	if(b=(CodeWord.getOtp()==i)) {
//		System.out.println(b);
		Logger log=Logger.getLogger(Landing.class);
		 String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/CalorieCounter/src/main/java/log4j.properties";
	       PropertyConfigurator.configure(log4jConfPath);
	       log.info("Your otp is correct");
		login obj1= new login();
		try {
			obj1.loginuser(Landing.con,response,request);
			System.out.println("redirect");
		   response.sendRedirect("TrainRegis.html");
		} catch (NoSuchAlgorithmException | SQLException e) {
		       PropertyConfigurator.configure(log4jConfPath);
		       log.debug("cookie is not added");
		}
	}
	else {
	   System.out.println("otp wrong");
	   response.sendRedirect("otp.html");
	}
	}
	}
}
