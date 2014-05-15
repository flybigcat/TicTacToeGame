package ttt.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;


/*	Test:
 There is one user with the username cysun.
 */
@Test(groups = "UserDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	UserDao userDao;

	// There is one user with the username cysun
	@Test
	public void getUser()
	{
		assert !userDao.getUser( "cysun" ).equals( null );
	}
	
	
//	@Test
//	public void getUsers()
//	{
//		assert userDao.getUsers().size() == 2;
//	}
	
}