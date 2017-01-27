package org.sbts.backend.service;

import org.sbts.backend.model.User;

public interface UserService {
	User save(User user);
	
	User findByUserName(String userName);
	


}
