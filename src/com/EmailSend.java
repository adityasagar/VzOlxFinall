package com;
	 import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

     public class EmailSend {
        private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
        private static final String SMTP_AUTH_USER = "xFBlqCdszg";
        private static final String SMTP_AUTH_PWD = "txxH436VA4kK5272";


        public void sendMail(ProductVO product,UserVO buyer,UserVO seller,String msg) throws Exception
        {
           Properties properties = new Properties();
           properties.put("mail.transport.protocol", "smtp");
           properties.put("mail.smtp.host", SMTP_HOST_NAME);
           properties.put("mail.smtp.port", 587);
           properties.put("mail.smtp.auth", "true");
           Authenticator auth = new SMTPAuthenticator();
           Session mailSession = Session.getDefaultInstance(properties, auth);
           MimeMessage message = new MimeMessage(mailSession);
           Multipart multipart = new MimeMultipart("alternative");
           BodyPart part1 = new MimeBodyPart();
           //part1.setText(msg);
          //BodyPart part2 = new MimeBodyPart(); 
          part1.setContent(
            "<p>Hello Mr./Ms./Mrs."+seller.getName()+",</p><p><b>Mr./Ms./Mrs."+buyer.getName()+"</b> is interested in your product <b>"+product.getName()+"</b>.</p><p>Contact Details: <br>"+buyer.getContact()+"<br>"+buyer.getEmail()+"</p><p><b>Message: </b>"+msg+"</p><p>Thank you,<br>Verizon Employee Classifieds</br></p>",
              "text/html");
           multipart.addBodyPart(part1);
          // multipart.addBodyPart(part2);
           System.out.println("TO: "+seller.getEmail());
           message.setFrom(new InternetAddress("Verizon_Employee_Classifieds"));
           message.addRecipient(Message.RecipientType.TO,
              new InternetAddress(seller.getEmail()));
           message.setSubject("Someone is Interested in Your Product");
           message.setContent(multipart);
           Transport transport = mailSession.getTransport();
        // Connect the transport object.
        transport.connect();
        // Send the message.
        transport.sendMessage(message, message.getAllRecipients());
        // Close the connection.
        transport.close();
        System.out.println("my part is done");
           
        }
        private class SMTPAuthenticator extends javax.mail.Authenticator {
        	public PasswordAuthentication getPasswordAuthentication() {
        	   String username = SMTP_AUTH_USER;
        	   String password = SMTP_AUTH_PWD;
        	   return new PasswordAuthentication(username, password);
        	}
        }
      

}
