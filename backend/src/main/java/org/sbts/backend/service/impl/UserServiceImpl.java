package org.sbts.backend.service.impl;

import org.sbts.backend.dao.UserDao;
import org.sbts.backend.model.User;
import org.sbts.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public User save (User user) {
		return userDao.save(user);
	}
	
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}
	

}
