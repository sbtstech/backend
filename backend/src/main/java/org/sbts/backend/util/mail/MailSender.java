package org.sbts.backend.util.mail;

import javax.mail.MessagingException;

/**
 * The mail sender interface for sending mail
 * 
  */
public interface MailSender {

	public abstract void send(String to, String subject, String body) throws MessagingException;

}