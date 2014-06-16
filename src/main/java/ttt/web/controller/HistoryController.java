package ttt.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;
import ttt.security.SecurityUtils;

@Controller
public class HistoryController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GameDao gameDao;

	/*
	 * 1. The number of games completed (i.e. excluding the saved games). 2. The
	 * number of 1-player games completed. 3. The number of 2-player games
	 * completed. 4. The win rate against AI. 5. The win rate against human
	 * players. 6. The list of games played this month. For each game, show the
	 * opponent's name (username for a human player, or "AI" for the AI player),
	 * game length (i.e. end time - start time), and the outcome.
	 */
	@RequestMapping(value = "/game/history.html")
	public String history( HttpSession session, ModelMap models )
	{

		// if ( session.getAttribute( "player" ) == null )
		// {
		// session.setAttribute( "historyMark", 1 );
		// return "redirect:/user/login.html";
		// }
		//
		// User player = (User) session.getAttribute( "player" );

		User player = userDao.getUser( SecurityUtils.getUsername() );

		List<Game> gameAI = gameDao.getGamesAgainstAI( player );
		List<Game> gamePlayer1 = gameDao.getGamesAsPlayer1AgainstHuman( player );
		List<Game> gamePlayer2 = gameDao.getGamesAsPlayer2AgainstHuman( player );

		List<Game> gameAIMonth = new ArrayList<Game>();
		List<Game> gamePlayer1Month = new ArrayList<Game>();
		List<Game> gamePlayer2Month = new ArrayList<Game>();

		int gameAIN = 0;
		int gamePlayer1N = 0;
		int gamePlayer2N = 0;

		float winAI = -1;
		float winHuman = -1;

		Calendar curCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();

		int curYear = curCalendar.get( Calendar.YEAR );
		int curMonth = curCalendar.get( Calendar.MONTH );

		if ( gameAI != null && gameAI.size() != 0 )
		{
			gameAIN = gameAI.size();
			int win = 0;
			for ( int i = 0; i < gameAI.size(); i++ )
			{
				if ( gameAI.get( i ).getOutcome().equals( "win" ) )
					++win;

				calendar.setTime( gameAI.get( i ).getEndDate() );
				if ( curYear == calendar.get( Calendar.YEAR )
						&& curMonth == calendar.get( Calendar.MONTH ) )
				{
					gameAIMonth.add( gameAI.get( i ) );
				}
			}
			winAI = (float) win / (float) gameAI.size();
		}

		int win1 = 0;
		if ( gamePlayer1 != null && gamePlayer1.size() != 0 )
		{
			gamePlayer1N = gamePlayer1.size();

			for ( int i = 0; i < gamePlayer1.size(); i++ )
			{
				if ( gamePlayer1.get( i ).getOutcome().equals( "win" ) )
					++win1;

				calendar.setTime( gamePlayer1.get( i ).getEndDate() );
				if ( curYear == calendar.get( Calendar.YEAR )
						&& curMonth == calendar.get( Calendar.MONTH ) )
				{
					gamePlayer1Month.add( gamePlayer1.get( i ) );
				}
			}
		}

		int win2 = 0;
		if ( gamePlayer2 != null && gamePlayer2.size() != 0 )
		{
			gamePlayer2N = gamePlayer2.size();

			for ( int i = 0; i < gamePlayer2.size(); i++ )
			{
				if ( gamePlayer2.get( i ).getOutcome().equals( "loss" ) )
					++win2;

				calendar.setTime( gamePlayer2.get( i ).getEndDate() );
				if ( curYear == calendar.get( Calendar.YEAR )
						&& curMonth == calendar.get( Calendar.MONTH ) )
				{
					gamePlayer2Month.add( gamePlayer2.get( i ) );
				}
			}
		}

		if ( gamePlayer1N + gamePlayer2N != 0 )
		{
			winHuman = (float) ( win1 + win2 )
					/ (float) ( gamePlayer1N + gamePlayer2N );
		}

		models.put( "player", player );
		// 1. The number of games completed (i.e. excluding the saved games).
		models.put( "totalN", gameAIN + gamePlayer1N + gamePlayer2N );
		// 2. The number of 1-player games completed.
		models.put( "OnePlayerN", gameAIN );
		// 3. The number of 2-player games completed.
		models.put( "TwoPlayersN", gamePlayer1N + gamePlayer2N );
		// 4. The win rate against AI.
		models.put( "winAI", winAI );
		// 5. The win rate against human players.
		models.put( "winHuman", winHuman );

		// 6. The list of games played this month. For each game, show the
		// opponent's name (username for a human player, or "AI" for the AI
		// player),
		// game length (i.e. end time - start time), and the outcome.
		models.put( "gameAIMonth", gameAIMonth );
		models.put( "gamePlayer1Month", gamePlayer1Month );
		models.put( "gamePlayer2Month", gamePlayer2Month );

		return "/game/history";
	}

	@RequestMapping(value = "/game/savedGameHistory.html")
	public String savedGameHistory( ModelMap models, HttpSession session )
	{

		// if ( session.getAttribute( "player" ) == null )
		// {
		// session.setAttribute( "savehistoryMark", 1 );
		// return "redirect:/user/login.html";
		// }
		//
		// User player = (User) session.getAttribute( "player" );

		User player = userDao.getUser( SecurityUtils.getUsername() );

		models.put( "player", player );
		models.put( "savedGames", gameDao.getSavedGames( player ) );
		return "/game/savedGameHistory";
	}
}
