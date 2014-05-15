package ttt.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import ttt.model.Game;

/*	Test:
 There are two games played by cysun against the AI player; one of them is a win and the other one a loss.
 There is one game saved by cysun, and in the game cysun occupied the upper-left cell and the AI player occupied the center cell.
 */
@Test(groups = "GameDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GameDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	GameDao gameDao;
	
	@Autowired
	UserDao userDao;

	// there are two games played by cysun against the AI player;
	// one of them is a win and the other one a loss.
	@Test
	public void getGamesAgainstAI()
	{
		// two games played by cysun against the AI player
		boolean gameNumber = false;
		// one is win, the other is loss
		boolean check1 = false;
		boolean check2 = false;

		List<Game> games = gameDao
				.getGamesAgainstAI( userDao.getUser( "cysun" ) );

		if ( games != null )
		{
			gameNumber = ( games.size() == 2 );

			if ( gameNumber )
			{
				check1 = games.get( 0 ).getOutcome().equalsIgnoreCase( "win" )
						&& games.get( 1 ).getOutcome()
								.equalsIgnoreCase( "loss" );

				check2 = games.get( 1 ).getOutcome().equalsIgnoreCase( "win" )
						&& games.get( 0 ).getOutcome()
								.equalsIgnoreCase( "loss" );
			}
		}

		assert gameNumber && ( check1 || check2 );
	}

	// There is one game saved by cysun,
	// and in the game cysun occupied the upper-left cell
	// and the AI player occupied the center cell.

	@Test
	public void getSavedGames()
	{
		// There is one game saved by cysun,
		boolean gameNumber = false;
		// cysun occupied the upper-left cell
		boolean check1 = false;
		// the AI player occupied the center cell.
		boolean check2 = false;

		List<Game> games = gameDao.getSavedGames( userDao.getUser( "cysun" ) );

		if ( games != null )
		{
			gameNumber = ( games.size() == 1 );
		}

		if ( gameNumber )
		{
			// cysun occupied the upper-left cell
			check1 = ( games.get( 0 ).getBoard().get( 0 ) == 1 );
			// AI player occupied the center cell // check2 =
			check2 = ( games.get( 0 ).getBoard().get( 4 ) == 2 );
		}

		assert gameNumber && check1 && check2;
	}
}