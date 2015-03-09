/*
 * Copyright (C) 2014, Teyssier Loic

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 */
package util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Loic Teyssier
 *
 */


public class SendMail 
    
   {
    
    public static void send(String to, String sub, 
                         String msg, final String user,final String pass)
    {
     
     //create an instance of Properties Class  
     Properties props = new Properties();
     
     /* Specifies the IP address of your default mail server
     for e.g if you are using gmail server as an email sever
     you will pass smtp.gmail.com as value of mail.smtp host. As shown here in the 
    coding. Change accordingly, if your email id is not an gmail id*/
     props.put("mail.smtp.host", AppConfig.getConfigValue("mail_smtp_host"));
     
     props.put("mail.smtp.port", AppConfig.getConfigValue("mail_smtp_port"));	//this is optional
     props.put("mail.smtp.auth", AppConfig.getConfigValue("mail_smtp_auth"));
     props.put("mail.smtp.starttls.enable", AppConfig.getConfigValue("mail_smtp_starttls_enable"));
     

     
     /*Pass Properties object(props) and Authenticator object   
           for authentication to Session instance */

    Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
  protected PasswordAuthentication getPasswordAuthentication() {
   return new PasswordAuthentication(user,pass);
   }
});
    
 try
 {
 
 /* Create an instance of MimeMessage, 
 it accept MIME types and headers */
 
 MimeMessage message = new MimeMessage(session);
 message.setFrom(new InternetAddress(user));
 message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
 message.setSubject(sub);
 message.setText(msg);

 /* Transport class is used to deliver the message to the recipients */
 Transport.send(message);
 
 
 }catch(Exception e)
 {
     e.printStackTrace();
 }
    }
    
}