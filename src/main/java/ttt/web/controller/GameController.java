package ttt.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;
import ttt.security.SecurityUtils;
import ttt.service.GameService;

@Controller
public class GameController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GameDao gameDao;

	@Autowired
	GameService gameService;

	/***************************** play & proceed 1-player game ***********************************/
	@RequestMapping(value = "/game/TicTacToeGame.html")
	public String game(
			ModelMap models,
			@RequestParam(value = "newGame", required = false) Integer newGame,
			@RequestParam(value = "i", required = false) Integer i,
			@RequestParam(value = "resumeGameID", required = false) Integer resumeGameID,
			HttpSession session )
	{

		/****************** Authenticated ******************/
		if ( SecurityUtils.isAuthenticated() )
		{
			// User player = (User) session.getAttribute( "player" );
			User player = userDao.getUser( SecurityUtils.getUsername() );
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
					&& ( (Game) session.getAttribute( "tttGame" ) )
							.getOutcome() != null
					&& !( (Game) session.getAttribute( "tttGame" ) )
							.getOutcome().equals( "" ) )
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

		/****************** Not Authenticated (Anonymous) ******************/
		if ( SecurityUtils.isAnonymous() )
		{
			System.out.println( "anonymous" );

			Game tttGame = null;

			// first time visit or NewGame

			// finish a game, and go to the new game from welcome page
			if ( session.getAttribute( "tttGame" ) == null
					|| (( (Game) session.getAttribute( "tttGame" ) )
							.getOutcome() != null
					&& !( (Game) session.getAttribute( "tttGame" ) )
							.getOutcome().equals( "" ))
					|| ( newGame != null && newGame == 1 ) )
			{
				tttGame = new Game();

				tttGame.setStartDate( new Date() );

				session.setAttribute( "tttGame", tttGame );

				System.out.println( "new start" );
				
				return "/game/TicTacToeGame";
			}

			// not first time
			if ( session.getAttribute( "tttGame" ) != null )
			{
				tttGame = (Game) session.getAttribute( "tttGame" );
			}

			// normal proceed game
			if ( i != null )
			{
				tttGame.getBoard().set( i, 1 );

				// check if player 1 win
				if ( tttGame.getOutcome() != null )
				{
					if ( tttGame.getOutcome().equals( "win" ) )
					{

						tttGame.setEndDate( new Date() );
						tttGame.setOutcome( "win" );
						models.put( "end", 1 );

						session.setAttribute( "tttGame", tttGame );

						return "/game/TicTacToeGame";
					}

					// check if tied
					if ( tttGame.getOutcome().equals( "tie" ) )
					{

						tttGame.setEndDate( new Date() );
						tttGame.setOutcome( "tie" );
						models.put( "end", 0 );

						session.setAttribute( "tttGame", tttGame );

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

					session.setAttribute( "tttGame", tttGame );

					return "/game/TicTacToeGame";
				}

				// check if player 1 can make win step
				int p1WS = tttGame.getP1WinStep();
				// it has win step
				if ( p1WS != -1 )
				{
					tttGame.getBoard().set( p1WS, 2 );

					session.setAttribute( "tttGame", tttGame );

					return "/game/TicTacToeGame";
				}

				// random move
				tttGame.getBoard().set( tttGame.getRandomStep(), 2 );
				session.setAttribute( "tttGame", tttGame );
			}

			return "/game/TicTacToeGame";
		}
		/*************************** should never get here **********************************/
		System.out.println( "error" );
		return null;
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

	/************************* 2-player game *****************************/
	@RequestMapping(value = "/game/TicTacToeGame2.html")
	public String game2( ModelMap models,
			@RequestParam(value = "playerRole") Integer playerRole,
			HttpSession session )

	{

		User player1 = new User();
		User player2 = new User();
		User player = userDao.getUser( SecurityUtils.getUsername() );

		if ( playerRole == 1 )
		{
			player1 = player;
			Game tttGame2 = new Game();
			tttGame2.setPlayer1( player1 );
			gameDao.setGame( tttGame2 );
			gameService.addGame( player1.getId(), tttGame2 );
		}

		if ( playerRole == 2 )
			player2 = player;

		models.put( "playerRole", playerRole );

		return "/game/TicTacToeGame2";
	}

	/**************************** waiting for another player ************************/
	@RequestMapping("/game/TicTacToeGame1.json")
	public String hostGameJson( ModelMap models )
	{
		models.put( "player1waiting",
				"Waiting for another player to join the game ..." );
		return "jsonView";
	}

	@RequestMapping("/game/TicTacToeGame2.json")
	public String joinGameJson( ModelMap models )
	{
		models.put( "player2waiting",
				"Waiting for another player to host the game ..." );
		return "jsonView";
	}

	/************************ send request for matching **********************************/
	@RequestMapping("/game/TicTacToeGame1.deferred.json")
	@ResponseBody
	public DeferredResult<String> matchGameDeferred1()
	{

		System.out.println( "here1" );

		DeferredResult<String> result = new DeferredResult<String>();
		// save the result
		User player = new User();
		player = userDao.getUser( SecurityUtils.getUsername() );
		gameService.addResult4Match1( player.getId(), result );

		return result;
	}

	@RequestMapping("/game/TicTacToeGame2.deferred.json")
	@ResponseBody
	public DeferredResult<String> matchGameDeferred2()
	{

		System.out.println( "here2" );

		DeferredResult<String> result = new DeferredResult<String>();
		// save the result
		User player = new User();
		player = userDao.getUser( SecurityUtils.getUsername() );
		gameService.addResult4Match2( player.getId(), result );

		return result;
	}
	
	/************************ send request for moving by turn*****************************/
	@RequestMapping("/game/TicTacToeGame.deferred.json")
	@ResponseBody
	public DeferredResult<String> GameDeferred()
	{

		DeferredResult<String> result = new DeferredResult<String>();
		// save the result
		User player = new User();
		player = userDao.getUser( SecurityUtils.getUsername() );
		gameService.addResult( player.getId(), result );

		return result;
	}

	@RequestMapping("/game/move1.html")
	public void move1(@RequestParam(value = "boardid", required = false) Integer boardid)
	{
		System.out.println( "move1:"+ boardid);

		User player = new User();
		player = userDao.getUser( SecurityUtils.getUsername() );
		
		System.out.println( "palyer1Id:"+ player.getId());
		gameService.addMove1( player.getId(), boardid );
	}
	
	@RequestMapping("/game/move2.html")
	public void move2(@RequestParam(value = "boardid", required = false) Integer boardid)
	{
		System.out.println( "move2:"+ boardid);
		
		User player = new User();
		player = userDao.getUser( SecurityUtils.getUsername() );
		
		System.out.println( "palyer2Id:"+ player.getId());
		gameService.addMove2( player.getId(), boardid );
	}
	
}
