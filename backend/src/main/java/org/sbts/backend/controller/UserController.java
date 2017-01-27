package org.sbts.backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.sbts.backend.model.User;
import org.sbts.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.sbts.backend.util.mail.SmtpMailSender;

@RestController
@RequestMapping ("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SmtpMailSender smtpMailSender;
	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
/**
	public SmtpMailSender getSmtpMailSender() {
		return smtpMailSender;
	}

	public void setSmtpMailSender(SmtpMailSender smtpMailSender) {
		this.smtpMailSender = smtpMailSender;
	}
**/
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public User registerUser(@RequestBody User user) throws MessagingException {
        System.out.println("Registration starting");
        System.out.println(user);
		
        if (user != null ) {
            System.out.println("Registration successful");
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            user.setToken(token);
            //SendEmailConfirmation mailActivation = new SendEmailConfirmation();
            String link =  "http://localhost:7070/RTH_Sample14/"+token ;
            System.out.println(user.getEmail());
            System.out.println( link );
            
            smtpMailSender.send(user.getEmail() , "Email verificaton" , "Please verifiy your email " + link ); 
            // return Response.ok(response).build();
            
        }
        else {
            // return Response.status(Response.Status.UNAUTHORIZED).type("text/plain").entity("Registration failed!").build();
        }
		User u = userService.save(user);
		return u ; 
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(@RequestBody Map<String, String> json) throws ServletException {
		
		if (json.get("username")==null || json.get("password") == null) {
			throw new ServletException ("Please fill in username and password");
		}
		
		String userName =json.get("username");
		String password = json.get("password");
		
		User user = userService.findByUserName(userName);
		
		if (user == null) {
			throw new ServletException ("User name not found.");
		}
		
		String pwd = user.getPassword();
		
		if (!password.equals(pwd)) {
			throw new ServletException("Invalid login. Please check your name and password.");
		}
		return Jwts.builder().setSubject(userName).claim("roles", "user").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
				
	}
	
	
}
