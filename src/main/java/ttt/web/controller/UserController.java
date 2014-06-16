package ttt.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ttt.web.validator.UserLoginValidator;
import ttt.web.validator.UserValidator;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;
import ttt.model.Game;
import ttt.model.User;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GameDao gameDao;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserLoginValidator userLoginValidator;

	/***************************** list all the users **********************************/
	@RequestMapping("/user/list.html")
	// anything you need, put in the argument or html?id=1
	public String users( ModelMap models )
	{
		// put("string key", object)
		models.put( "users", userDao.getUsers() );
		return "user/list";
	}

	/***************************** register **********************************/
	@RequestMapping(value = "/user/registration.html", method = RequestMethod.GET)
	public String add( ModelMap models )
	{
		models.put( "user", new User() );
		return "user/registration";
	}

	@RequestMapping(value = "/user/registration.html", method = RequestMethod.POST)
	public String addUser( @ModelAttribute User user,
			BindingResult bindingResult, SessionStatus sessionStatus )
	{
		userValidator.validate( user, bindingResult );
		if ( bindingResult.hasErrors() )
			return "user/registration";

		userDao.setUser( user );
		sessionStatus.setComplete();

		//return "redirect:/spring_security_login";
		
		return "redirect:../login.jsp";
	}

	/***************************** login **********************************/

	@RequestMapping(value = "/user/login.html", method = RequestMethod.GET)
	public String login( ModelMap models, HttpSession session )
	{
		if ( session.getAttribute( "player" ) == null )
		{
			models.put( "user", new User() );
			return "user/login";
		}

		return "redirect:/game/TicTacToeGame.html";
	}

	@RequestMapping(value = "/user/login.html", method = RequestMethod.POST)
	public String loginUser( @ModelAttribute User user,
			BindingResult bindingResult, SessionStatus sessionStatus,
			HttpSession session )
	{

		userLoginValidator.validate( user, bindingResult );
		if ( bindingResult.hasErrors() )
			return "user/login";

		session.setAttribute( "player", userDao.getUser( user.getUsername() ) );

		sessionStatus.setComplete();

		boolean historyMark = false;
		if ( session.getAttribute( "historyMark" ) != null )
		{
			historyMark = true;
			session.setAttribute( "historyMark", null );
		}

		if ( historyMark )
			return "redirect:/game/history.html";
		
		boolean savehistoryMark = false;
		if ( session.getAttribute( "savehistoryMark" ) != null )
		{
			savehistoryMark = true;
			session.setAttribute( "savehistoryMark", null );
		}
		
		if ( savehistoryMark )
			return "redirect:/game/savedGameHistory.html";
		
		int twoPlayers = 0;
		if ( session.getAttribute( "twoPlayers" ) != null )
		{
			twoPlayers = (int) session.getAttribute( "twoPlayers" );
			session.setAttribute( "twoPlayers", null );
		}

		if ( twoPlayers ==1 )
			return "redirect:/game/TicTacToeGame2.html?playerRole=1";
		
		if ( twoPlayers ==2 )
			return "redirect:/game/TicTacToeGame2.html?playerRole=2";

		return "redirect:/game/TicTacToeGame.html";
	}

	/***************************** logout **********************************/
	@RequestMapping(value = "/user/logout.html", method = RequestMethod.GET)
	public String logout( HttpSession session )
	{
		Game tttGame = null;

		if ( session.getAttribute( "tttGame" ) != null )
		{
			tttGame = (Game) session.getAttribute( "tttGame" );
		}

		if ( tttGame != null
				&& ( tttGame.getOutcome() == null || tttGame.getOutcome()
						.equals( "" ) ) )
		{
			tttGame.setEndDate( new Date() );
			tttGame.setOutcome( "loss" );

			tttGame = gameDao.setGame( tttGame );
		}

		session.invalidate();

	//	return "user/logout";
		return "redirect:/j_spring_security_logout";

	}

}