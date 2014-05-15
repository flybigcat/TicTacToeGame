package ttt.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ttt.model.User;
import ttt.model.dao.UserDao;

@Component
public class UserValidator implements Validator {

	@Autowired
    private UserDao userDao;
	
    @Override
    public boolean supports( Class<?> clazz )
    {
    	// return true of clazz is User.class or a subclass
        return User.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        User user = (User) target; 	
                
        if( !StringUtils.hasText( user.getUsername() ) )
            errors.rejectValue( "username", "error.user.username.empty" );
        
        if( userDao.getUser( user.getUsername() ) != null)
            errors.rejectValue( "username", "error.user.username.notunique" );

        if( !StringUtils.hasText( user.getPassword() ) )
            errors.rejectValue( "password", "error.user.password.empty" );
        
        if( !StringUtils.hasText( user.getEmail() ) )
            errors.rejectValue( "email", "error.user.email.empty" );
    }

}
