package ttt.model.dao;

import java.util.List;
import ttt.model.User;

public interface UserDao {

	User getUser( Integer id );

	// Retrieve a user from the database given the user's username
	// User getUser( String username )
	User getUser( String username );

	List<User> getUsers();

	User setUser(User user);

}