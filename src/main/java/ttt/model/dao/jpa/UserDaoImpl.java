package ttt.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.User;
import ttt.model.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User getUser( Integer id )
	{
		return entityManager.find( User.class, id );
	}

	@Override
	public List<User> getUsers()
	{
		return entityManager.createQuery( "from User order by id", User.class )
				.getResultList();
	}

	// Retrieve a user from the database given the user's username
	@Override
	public User getUser( String username )
	{

		List<User> users = entityManager
				.createQuery( "from User where username = :username",
						User.class ).setParameter( "username", username )
				.getResultList();

		if ( users != null && users.size() == 1 )
		{
			return users.get( 0 );
		}

		else
			return null;

	}

	@Transactional
	@Override
	public User setUser( User user )
	{
		return user = entityManager.merge( user );
	}
}