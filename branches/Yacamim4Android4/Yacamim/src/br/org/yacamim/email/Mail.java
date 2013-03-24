/**
 * Mail.java
 *
 */
package br.org.yacamim.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

/**
 * 
 * Class Mail TODO
 * 
 * @author Community
 * @version 1.0
 * @since 1.0
 */
public class Mail extends Authenticator {
	
	  private String user; 
	  private String password; 
	 
	  private String[] to; 
	  private String from; 
	 
	  private String port; 
	  private String sport; 
	 
	  private String host; 
	 
	  private String subject; 
	  private String body; 
	 
	  private boolean auth; 
	   
	  private boolean debuggable; 
	 
	  private Multipart multipart;

	/**
	 * 
	 */
	public Mail() {
		super();
		this.init();
	}
	
	/**
	 * 
	 * @param _user
	 * @param _password
	 */
	public Mail(final String _user, final String _password) { 
		this(); 
		this.init();
		this.user = _user; 
		this.password = _password; 
	} 
	
	/**
	 * 
	 */
	private void init() {
		try {
			this.host = "smtp.gmail.com"; // default smtp server 
			this.port = "465"; // default smtp port 
			this.sport = "465"; // default socketfactory port 
		 
			this.user = ""; // username 
			this.password = ""; // password 
			this.from = ""; // email sent from 
			this.subject = ""; // email subject 
			this.body = ""; // email body 
		 
			this.debuggable = false; // debug mode on or off - default off 
		    this.auth = true; // smtp authentication - default on 
		 
		    this.multipart = new MimeMultipart(); 
		 
		    // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
		    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		    CommandMap.setDefaultCommandMap(mc); 
		} catch (Exception _e) {
			Log.e("Email.init ", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _filename
	 * @throws Exception
	 */
	public void addAttachment(final String _filename) throws Exception { 
	    BodyPart messageBodyPart = new MimeBodyPart(); 
	    DataSource source = new FileDataSource(_filename); 
	    messageBodyPart.setDataHandler(new DataHandler(source)); 
	    messageBodyPart.setFileName(_filename); 
	 
	    multipart.addBodyPart(messageBodyPart); 
	} 
	
	/**
	 * 
	 * @param _imageFile
	 * @throws Exception
	 */
	public void addAttachment(final File _imageFile) throws Exception { 
		BodyPart messageBodyPart = new MimeBodyPart(); 
		DataSource source = new FileDataSource(_imageFile); 
		messageBodyPart.setDataHandler(new DataHandler(source)); 
		messageBodyPart.setFileName(_imageFile.getName()); 
		
		multipart.addBodyPart(messageBodyPart); 
	}
	
	/**
	 * 
	 */
	public boolean send() throws Exception { 
	    Properties props = setProperties(); 
	 
		if(!this.user.equals("") && !this.password.equals("") && this.to.length > 0 && !this.from.equals("") && !this.subject.equals("") && !this.body.equals("")) { 
			Session session = Session.getInstance(props, this); 
			 
			MimeMessage msg = new MimeMessage(session); 
			 
			msg.setFrom(new InternetAddress(from)); 
			       
			InternetAddress[] addressTo = new InternetAddress[to.length]; 
			for (int i = 0; i < to.length; i++) { 
				addressTo[i] = new InternetAddress(to[i]); 
			} 
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 
			 
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
	 
	/**
	 * 
	 *
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	@Override 
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(user, password); 
	} 
	
	/**
	 * 
	 * @return
	 */
	private Properties setProperties() { 
		final Properties props = new Properties(); 
		 
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
	 
	/**
	 * 
	 * @return
	 */
	public String getBody() { 
		return body; 
	} 
		
	/**
	 * 
	 * @param _body
	 */
	public void setBody(String _body) { 
		this.body = _body; 
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getTo() {
		return to;
	}
	
	/**
	 * 
	 * @param to
	 */
	public void setTo(String[] to) {
		this.to = to;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * 
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	} 

}