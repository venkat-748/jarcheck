package com.train;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public void sendMail(String recp,int otp){
	    Properties properties=new Properties();
	    properties.put("mail.smtp.auth","true");
	    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    properties.put("mail.smtp.starttls.enable","true");
	    properties.put("mail.smtp.host","smtp.gmail.com");
	    properties.put("mail.smtp.port","587");

	    String username="manmade5051@gmail.com";
	    String password="securecooker@5051";

	    Session session=Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username,password);
	        }
	    });
        System.out.println(otp);
	    Message message=prepareMessage(session,username,recp,otp);
	    try {
	        Transport.send((javax.mail.Message) message);
	    }catch (Exception e){
	       
	    }
	}
	public static Message prepareMessage(Session session,String username,String recp,int otp){
	    Message message=new MimeMessage(session);
	    try {
	        message.setFrom(new InternetAddress(username));
	        message.setRecipient(Message.RecipientType.TO,new InternetAddress(recp));
	        System.out.println("sending");
	        message.setSubject("Rail ticket otp verification.");
	        message.setText("Your otp is "+otp);
	        System.out.println("sended");
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return message;
	}

}
