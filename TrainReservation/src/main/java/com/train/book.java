package com.train;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class book
 */
public class book extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String user_id="";
	int seat=0;
	int no_tickets=0;
	Logger log = Logger.getLogger(book.class);
	 String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/TicketResevation/src/main/java/log4j/log4j.properties";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ticket tick = new ticket();
		 System.out.println("hello");
		 System.out.println(ticket.date1);
		PropertyConfigurator.configure(log4jConfPath);
			int train_id =Integer.parseInt(request.getParameter("train_id"));
			int tickets = Integer.parseInt(request.getParameter("tickets"));
			String date = request.getParameter("date");
			 boolean status = false;
				try {
			
					if(datecheck.dateCheck(date)) {
					
						status =true;
						System.out.println("success");
					}
					else {
					
						status=false;
						System.out.println("failed");
						response.sendRedirect("Booking.html");
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			try {
				booking(train_id, tickets, date,request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public boolean booking(int train_id,int tickets,String date, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ParseException {
		ticket tick = new ticket();
		 PrintWriter out = response.getWriter();
		
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		    LocalDateTime now = LocalDateTime.now();
		    String today = dtf.format(now);
		    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");  
		    LocalDateTime now2 = LocalDateTime.now(); 
		    String time = dtf2.format(now);
		    Cookie ck[] = request.getCookies();
		    for(Cookie ckValue: ck) {
	System.out.println(ckValue.getValue());
		      if(ckValue.getName().equals("user_id")) {
		    	  System.out.println(ckValue.getValue());
		    		user_id=ckValue.getValue();
		      }
		        
		    }
		    
		    Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Train","root","");
	        PreparedStatement stmt;
	        stmt = con.prepareStatement("select seat_avail from train where train_id=? and date=? and fromPlace=? and toPlace=?");
	        stmt.setInt(1, train_id);
	        stmt.setString(2,date );
	        stmt.setString(3,ticket.start1);
	        stmt.setString(4,ticket.end1);
	        ResultSet rs = stmt.executeQuery();
	        while(rs.next()) {
	        	seat=rs.getInt(1);
	        }
		    stmt = con.prepareStatement("insert into ticketHistory(train_id,time,date,booking_date,user_id) values(?,?,?,?,?)");
		    stmt.setInt(1, train_id);
		    stmt.setString(2, time);
		    stmt.setString(3,date );
		    stmt.setString(4,today);
		    stmt.setString(5,user_id);
	         System.out.println("insert data into ticketHistory:"+stmt);
		    if(stmt.executeUpdate()==1) {
		    	System.out.println("inserted completely");
		    }
			
		   
		    stmt = con.prepareStatement("update train set seat_avail=? where train_id=? and date=? and fromPlace=? and toPlace=?");
		    stmt.setInt(1,seat-tickets);
		    stmt.setInt(2, train_id);
		    stmt.setString(3, date);
		    stmt.setString(4,ticket.start1);
	        stmt.setString(5,ticket.end1);
		    System.out.println("update seat  into train:"+stmt);
	        if(stmt.executeUpdate()==1) {
	        	System.out.println(" seat update ");
	        }
	        else {
	        	System.out.println("seat not update");
	        }
	       
	        
	        stmt = con.prepareStatement("select  no_of_tickets from users where user_id=?");
		    stmt.setString(1, user_id);
		    System.out.println("select ticketcount:"+stmt);
	     ResultSet rs1 = stmt.executeQuery();
	     while(rs1.next()) {
	    	 System.out.println(rs1.getInt(1));
	    	 no_tickets =rs1.getInt(1);
	     }
	     System.out.println(no_tickets);
	     
	        stmt = con.prepareStatement("update users set no_of_tickets=? where user_id=?");
		    stmt.setInt(1,no_tickets+tickets);
		    System.out.println(no_tickets+tickets);
	        stmt.setString(2, user_id);
	        System.out.println("update no_of_tickets  into users:"+stmt);
	        if(stmt.executeUpdate()==1) {
	        	System.out.println(" ticket updated ");
	        }
	        else {
	        	System.out.println("seat not updated");
	        }
	        out.print("<h1>Succes</h1>");
	        return true;
	       
		}

	}

