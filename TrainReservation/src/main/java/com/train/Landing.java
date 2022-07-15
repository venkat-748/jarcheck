package com.train;
import java.math.BigInteger;
import java.nio.channels.SelectableChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.Resultset;
class CodeWord{
	private static int otp;
	public static int getOtp() {
		return otp;
	}
	public static void setOtp(int otp) {
		CodeWord.otp = otp;
	}
}
public class Landing {
	public static int otp;
	static String email;
	static String password;
	static String uname;
	static String phone;
	static String userid;
	public static Connection con;
	public Landing(String email, String password, String uname, String phone) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
	System.out.println("hello");
	this.email=email;
	this.password=password;
	this.uname=uname;
	this.phone=phone;
//	System.out.println(""+email+""+password+""+uname+""+phone+"");
	Class.forName("com.mysql.cj.jdbc.Driver");
	con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Train","root","");
     this.userid=userId();
      CodeWord.setOtp(otp());
      this.otp =CodeWord.getOtp();
      Mail obj = new Mail();
		System.out.println(Landing.email+" "+Landing.otp);
		System.out.println(Landing.otp);
		obj.sendMail(Landing.email,Landing.otp);
		System.out.println("sended sucessfully");
    
     
}
public static String hashPassword(String password) throws NoSuchAlgorithmException{
	String salt="qwertyuiopoigfdsacvbnm,237890!@#$%^&*QWERTYUIKJHGFDSCVHJITRESXCVBL:";
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest((password+salt).getBytes());
    StringBuilder builder=new StringBuilder(new BigInteger(1, messageDigest).toString(16));
    return builder.reverse().toString();
}
public String userId() {
	 Random random = new Random() ;
     String str = "abcdefghijklmnopqrstuvwxyzABCDEGFGHIJKLMNOPQRSTUVWXYZ0123456789";	
     String userid = "";
     for(int i=0;i<20;i++) {
    	 userid+=str.charAt(random.nextInt(61)+ 0);
     }
     return userid;
}
public static Connection getCon() throws ClassNotFoundException, SQLException {
	Class.forName("com.mysql.cj.jdbc.Driver");
	return DriverManager.getConnection("jdbc:mysql://localhost:3306/Train","root","");
}
public int otp(){
	 Random random = new Random() ;
     return random.nextInt(1000)+9999;
}
public static boolean signinCheck(String email, String password) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
	if(con==null) {
		con=getCon();
	}
	PreparedStatement stmt = con.prepareStatement("select username from users where email=? and password=?");
	stmt.setString(1, email);
	stmt.setString(2, hashPassword(password));
	System.out.println(stmt);
	ResultSet rs = stmt.executeQuery();
	System.out.println(stmt); 
    return rs.next();
}
}
