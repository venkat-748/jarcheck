package  com.train;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class login {
	Logger log=Logger.getLogger(login.class);
	 String log4jConfPath = "/home/venkat-zstk271/eclipse-workspace/TicketReservation/src/main/java/log4j.properties";
     
	boolean loginuser(Connection con,HttpServletResponse response,HttpServletRequest request) throws SQLException, NoSuchAlgorithmException, IOException {
		System.out.println("coming");
		PreparedStatement stmt = con.prepareStatement("insert into users(user_id,Phone,username,email,password) values(?,?,?,?,?)");
		System.out.println(Landing.uname);
		stmt.setString(1,Landing.userid);
		stmt.setString(3,Landing.uname);
		stmt.setString(4,Landing.email);
		stmt.setLong(2,Long.parseLong(Landing.phone));
		stmt.setString(5, Landing.hashPassword(Landing.password));
		System.out.println(stmt);
		int i=stmt.executeUpdate();
		System.out.println(i);
		if(i==1) {
			Cookie ck = new Cookie("user_id",Landing.userid);
		    ck.setMaxAge(24*60*60); 
		    System.out.println(ck);
		    System.out.println("otp");
		    response.addCookie(ck);
			System.out.println("hello");
		       PropertyConfigurator.configure(log4jConfPath);
		       log.debug("the value is insert into database");
			return true;
		}
		else {
			System.out.println("bad");
		       PropertyConfigurator.configure(log4jConfPath);
		       log.debug("the value is insert into database");
		return false;
		
	}
	}
}
