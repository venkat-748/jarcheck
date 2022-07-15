package com.train;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	Logger log=Logger.getLogger(Signup.class);
	String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/TicketReservation/src/main/java/log4j.properties";
	private static final long serialVersionUID = 1L;
	HttpServletResponse response;
	HttpServletRequest request;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("signup");
		this.response=response;
		this.request=request;
	     String email= request.getParameter("email");
	     String password= request.getParameter("pass");
	     String uname= request.getParameter("name");
	     String phone= request.getParameter("phone");
	     System.out.println(uname);
	     try {
			if(checking(email,password,uname,phone,response)) {
				System.out.println("checking");
				request.getRequestDispatcher("otp.html").forward(request, response);
			}
		} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
			log.debug("checking method is not calling");
			e.printStackTrace();
		} 
	}
	public boolean checking(String email, String password, String uname, String dob,HttpServletResponse response) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, IOException, ServletException {
	PreparedStatement stmt ;
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Train","root","");
	log.info("get Connection From database");
	stmt = con.prepareStatement("select email from users");
	System.out.println(stmt);
	ResultSet rs = stmt.executeQuery();
	boolean duplicate = false;
	while (rs.next()) {
		if(rs.getString(1).equals(email)) {
			System.out.println(rs.getString(1));
			duplicate=true;
			break;
		}
		else {
			System.out.println(rs.getString(1));
			System.out.println("sucesss........");
			
		}
	}
	if(!duplicate) {
		Landing landing = new Landing(email, password, uname, dob);
		System.out.println("no duplicate");
		return true;
	}
	else {
		PrintWriter out = response.getWriter();
	System.out.println("not correct");
	out.println("already an existing user");
	response.sendRedirect("Signin.html");
	}
	return false;	
}
}