package com.train;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ticket")
public class ticket extends HttpServlet {
	public static String date1 ="";
	public static String start1="";
	public static String end1="";
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ticket.class);
	 String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/TicketResevation/src/main/java/log4j/log4j.properties";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PropertyConfigurator.configure(log4jConfPath);

		String date = request.getParameter("date");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		start1=start;
		end1=end;
		date1=date;
		System.out.println(date);
        boolean status = false;
		try {
	
			if(datecheck.dateCheck(date)) {
				
				status =true;
				System.out.println("success");
			}
			else {
				
				status=false;
				System.out.println("failed");
				
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		
	
		try {
			if(status)
			connect(date,start,end,request,response);
			else {
				
				response.sendRedirect("TrainRegis.html");
			}
		} catch (ClassNotFoundException | SQLException e) {
			
		} catch (InterruptedException e) {
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void connect(String date, String start, String end,HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Train","root","");
		
		PreparedStatement stmt = con.prepareStatement("select * from train where fromPlace=? and toPlace=? and Date=?");
		stmt.setString(1, start);
		stmt.setString(2, end);
		stmt.setString(3,  date);
		Time time;
		 
		System.out.println(stmt);
	    ResultSet rs = stmt.executeQuery();
	  
	    PrintWriter outPrintWriter = response.getWriter();

	    outPrintWriter.print(
	  			"<h1 style ='text-align:center'>Train  Information</h1><br><table border=\"1px\" style='text-align: center;margin:auto;border-collapse: collapse'>");
	   outPrintWriter.print(
	  			"<tr><td>train_id</td><td>seat_avail </td><td>Price</td><td>Date</td><td>Travel</td><td>Start_time</td><td>Arrive</td><td>FromPlace</td><td>ToPlace</td></tr>");
	  	while (rs.next()) {
	  		time = rs.getTime(6);
	  		System.out.println(time);
	  		if(!datecheck.timeCheck(time)) {
	  			response.sendRedirect("TrainRegis.html");
	  		}
	  		outPrintWriter.print("<tr><td>" + rs.getInt(1) + "</td><td>" + rs.getInt(2) + "</td><td> " + rs.getInt(3)
	  				+ "</td><td> " + rs.getDate(4) + "</td><td> " + rs.getTime(5) + "</td><td> " + rs.getTime(6)
	  				+ "</td><td> " + rs.getTime(7) + "</td><td> " + rs.getString(8) + "</td><td> "+ rs.getString(9)+ "</td></tr>");

	  	}
	  	outPrintWriter.print("</table>");
	  	outPrintWriter.println("<a style ='margin-left:50vw;font-size:20px'href='Booking.html'>Book</a>");
	    
}
}