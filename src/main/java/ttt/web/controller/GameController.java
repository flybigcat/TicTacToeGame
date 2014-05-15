package ttt.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;

@Controller
public class GameController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GameDao gameDao;

	/***************************** play & proceed game ***********************************/
	@RequestMapping(value = "/game/TicTacToeGame.html")
	public String game(
			ModelMap models,
			@RequestParam(value = "newGame", required = false) Integer newGame,
			@RequestParam(value = "i", required = false) Integer i,
			@RequestParam(value = "resumeGameID", required = false) Integer resumeGameID,
			HttpSession session )
	{

		// check login
		if ( session.getAttribute( "player" ) == null )
		{
			return "redirect:/user/login.html";
		}

		User player = (User) session.getAttribute( "player" );
		User player2 = userDao.getUser( 2 );

		Game tttGame = null;

		// resume a saved game
		if ( resumeGameID != null )
		{
			tttGame = gameDao.getGame( resumeGameID );
			session.setAttribute( "tttGame", tttGame );
			return "/game/TicTacToeGame";
		}

		// first time visit
		if ( session.getAttribute( "tttGame" ) == null )
		{
			tttGame = new Game();

			tttGame.setStartDate( new Date() );
			tttGame.setPlayer1( player );
			tttGame.setPlayer2( player2 );

			tttGame = gameDao.setGame( tttGame );
			session.setAttribute( "tttGame", tttGame );

			return "/game/TicTacToeGame";
		}

		// finish a game, and go to the new game from welcome page
		if ( session.getAttribute( "tttGame" ) != null
				&& ( (Game) session.getAttribute( "tttGame" ) ).getOutcome() != null
				&& !( (Game) session.getAttribute( "tttGame" ) ).getOutcome()
						.equals( "" ) )
		{
			tttGame = new Game();

			tttGame.setStartDate( new Date() );
			tttGame.setPlayer1( player );
			tttGame.setPlayer2( player2 );

			tttGame = gameDao.setGame( tttGame );
			session.setAttribute( "tttGame", tttGame );

			return "/game/TicTacToeGame";
		}

		// not first time
		if ( session.getAttribute( "tttGame" ) != null )
		{
			tttGame = (Game) session.getAttribute( "tttGame" );
		}

		// restart game without finish
		if ( newGame != null
				&& newGame == 1
				&& ( tttGame.getOutcome() == null || tttGame.getOutcome()
						.equals( "" ) ) )
		{
			tttGame.setEndDate( new Date() );
			tttGame.setOutcome( "loss" );
			tttGame = gameDao.setGame( tttGame );

			tttGame = new Game();
			tttGame.setStartDate( new Date() );
			tttGame.setPlayer1( player );
			tttGame.setPlayer2( player2 );
			tttGame = gameDao.setGame( tttGame );
			session.setAttribute( "tttGame", tttGame );

			return "/game/TicTacToeGame";
		}

		// restart game after finish
		if ( newGame != null && newGame == 1 )
		{
			tttGame = new Game();

			tttGame.setStartDate( new Date() );
			tttGame.setPlayer1( player );
			tttGame.setPlayer2( player2 );

			tttGame = gameDao.setGame( tttGame );
			session.setAttribute( "tttGame", tttGame );

			return "/game/TicTacToeGame";
		}

		// normal proceed game
		if ( i != null )
		{
			tttGame.getBoard().set( i, 1 );

			tttGame = gameDao.setGame( tttGame );

			// check if player 1 win
			if ( tttGame.getOutcome() != null )
			{
				if ( tttGame.getOutcome().equals( "win" ) )
				{

					tttGame.setEndDate( new Date() );
					tttGame.setOutcome( "win" );
					models.put( "end", 1 );

					tttGame = gameDao.setGame( tttGame );
					session.setAttribute( "tttGame", tttGame );

					return "/game/TicTacToeGame";
				}

				// check if tied
				if ( tttGame.getOutcome().equals( "tie" ) )
				{

					tttGame.setEndDate( new Date() );
					tttGame.setOutcome( "tie" );
					models.put( "end", 0 );

					tttGame = gameDao.setGame( tttGame );
					session.setAttribute( "tttGame", tttGame );
					System.out.println( "tie" );
					return "/game/TicTacToeGame";
				}
			}

			// check if player 2 can make win step
			int p2WS = tttGame.getP2WinStep();
			// it has win step
			if ( p2WS != -1 )
			{
				tttGame.getBoard().set( p2WS, 2 );

				tttGame.setEndDate( new Date() );
				tttGame.setOutcome( "loss" );
				models.put( "end", 2 );

				tttGame = gameDao.setGame( tttGame );
				session.setAttribute( "tttGame", tttGame );

				return "/game/TicTacToeGame";
			}

			// check if player 1 can make win step
			int p1WS = tttGame.getP1WinStep();
			// it has win step
			if ( p1WS != -1 )
			{
				tttGame.getBoard().set( p1WS, 2 );
				tttGame = gameDao.setGame( tttGame );
				session.setAttribute( "tttGame", tttGame );

				return "/game/TicTacToeGame";
			}

			// random move
			tttGame.getBoard().set( tttGame.getRandomStep(), 2 );

			tttGame = gameDao.setGame( tttGame );
			session.setAttribute( "tttGame", tttGame );
		}
		return "/game/TicTacToeGame";
	}

	/***************************** save Game ***********************************/
	@RequestMapping(value = "/game/saveGame.html")
	public String saveGame( HttpSession session )
	{
		Game tttGame = (Game) session.getAttribute( "tttGame" );

		tttGame.setSaveDate( new Date() );
		tttGame = gameDao.setGame( tttGame );
		session.setAttribute( "tttGame", null );

		return "redirect:/game/savedGameHistory.html";
	}
}
