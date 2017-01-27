package org.sbts.backend.dao;

import org.sbts.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long>{
	User save(User user);
	
	User findByUserName (String userName);
	

}
