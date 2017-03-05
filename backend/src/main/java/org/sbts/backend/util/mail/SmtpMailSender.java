package org.sbts.backend.util.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * An SMTP mail sender, which sends mails
 * using an injected JavaMailSender.
 * 
 *
 */
@Component
public class SmtpMailSender implements MailSender {
	
	private static final Log log = LogFactory.getLog(SmtpMailSender.class);

	@Autowired
	private JavaMailSender javaMailSender;
	
	public SmtpMailSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Setter method for injecting a JavaMailSender.
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * Sends a mail using a MimeMessageHelper
	 */
	@Override
	@Async
	public void send(String to, String subject, String body)
			throws MessagingException {
		
		log.info("Sending SMTP mail from thread " + Thread.currentThread().getName()); // toString gives more info    	

		// create a mime-message
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		// create a helper
		helper = new MimeMessageHelper(message, true); // true indicates
													   // multipart message
		// set the attributes
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true); // true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  
		
		//send the mail
		javaMailSender.send(message);
		
		log.info("Sent SMTP mail from thread " + Thread.currentThread().getName());    	
	}

}