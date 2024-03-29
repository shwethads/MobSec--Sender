package com.example.appb;

import java.util.Date; 
import java.util.Properties; 
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.activation.MailcapCommandMap; 
import javax.mail.BodyPart; 
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 
 
 
public class Mail extends javax.mail.Authenticator { 
  private String user; 
  private String pass; 
 
  private String to; 
  private String from; 
 
  private String port; 
  private String sport; 
 
  private String host; 
 
  private String subject; 
  private String body; 
 
  private boolean auth; 
   
  private boolean debuggable; 
 
  private Multipart multipart; 
 
 
  public Mail() { 
    host = "smtp.gmail.com"; // default smtp server 
    port = "465"; // default smtp port 
    sport = "465"; // default socketfactory port 
 
    user = ""; // username 
    pass = ""; // password 
    from = ""; // email sent from 
    subject = ""; // email subject 
    body = ""; // email body 
 
    debuggable = false; // debug mode on or off - default off 
    auth = true; // smtp authentication - default on 
 
    multipart = new MimeMultipart(); 
 
    // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
    CommandMap.setDefaultCommandMap(mc); 
  } 
 
  public Mail(String user, String pass, String to) { 
    this(); 
    this.user = user; 
    this.pass = pass; 
    from = user;
    this.to = to;
  } 
 
  public boolean send() throws Exception { 
    Properties props = _setProperties(); 
 
    if(!user.equals("") && !pass.equals("") && !from.equals("")) { 
      Session session = Session.getInstance(props, this); 
 
      MimeMessage msg = new MimeMessage(session); 
 
      msg.setFrom(new InternetAddress(from)); 
        msg.setRecipients(MimeMessage.RecipientType.TO, to); 
        
      msg.setSubject(subject); 
      msg.setSentDate(new Date()); 
 
      // setup message body 
      BodyPart messageBodyPart = new MimeBodyPart(); 
      messageBodyPart.setText(body); 
      multipart.addBodyPart(messageBodyPart); 
 
      // Put parts in message 
      msg.setContent(multipart); 
 
      // send email 
      Transport.send(msg); 
 
      return true; 
    } else { 
      return false; 
    } 
  } 
 
  public void addAttachment(String filename) throws Exception { 
    BodyPart messageBodyPart = new MimeBodyPart(); 
    DataSource source = new FileDataSource(filename); 
    messageBodyPart.setDataHandler(new DataHandler(source)); 
    messageBodyPart.setFileName(filename); 
 
    multipart.addBodyPart(messageBodyPart); 
  } 
 
  @Override 
  public PasswordAuthentication getPasswordAuthentication() { 
    return new PasswordAuthentication(user, pass); 
  } 
 
  private Properties _setProperties() { 
    Properties props = new Properties(); 
 
    props.put("mail.smtp.host", host); 
 
    if(debuggable) { 
      props.put("mail.debug", "true"); 
    } 
 
    if(auth) { 
      props.put("mail.smtp.auth", "true"); 
    } 
 
    props.put("mail.smtp.port", port); 
    props.put("mail.smtp.socketFactory.port", sport); 
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
    props.put("mail.smtp.socketFactory.fallback", "false"); 
 
    return props; 
  } 
 
  // the getters and setters 
  public String getBody() { 
    return body; 
  } 
 
  public String get_to() {
	return to;
}

public void set_to(String _to) {
	this.to = _to;
}

public String get_from() {
	return from;
}

public void set_from(String _from) {
	this.from = _from;
}

public String get_subject() {
	return subject;
}

public void set_subject(String _subject) {
	this.subject = _subject;
}

public void setBody(String _body) { 
    this.body = _body; 
  } 
 
  // more of the getters and setters �.. 
} 

