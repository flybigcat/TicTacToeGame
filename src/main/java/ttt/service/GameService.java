package ttt.service;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.Game;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GameService {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	UserDao userDao;

	@Autowired
	GameDao gameDao;

	// player1, game
	Map<Integer, Game> games;

	// "who sent request", game
	Map<Integer, DeferredResult<String>> results;

	// player 1 place holder
	Map<Integer, DeferredResult<String>> results4match1;

	// player 2 place holder
	Map<Integer, DeferredResult<String>> results4match2;

	private static final Logger logger = LoggerFactory
			.getLogger( GameService.class );

	public GameService()
	{
		games = new HashMap<Integer, Game>();
		results = new HashMap<Integer, DeferredResult<String>>();
		results4match1 = new HashMap<Integer, DeferredResult<String>>();
		results4match2 = new HashMap<Integer, DeferredResult<String>>();
	}

	/**************************************************************************/
	public Map<Integer, Game> getGames()
	{
		return games;
	}

	public Game getGame( Integer player1 )
	{
		return games.get( player1 );
	}

	public void addGame( Integer id1, Game game )
	{
		games.put( id1, game );
		logger.debug( game + " added." );
	}

	public void removeGame( Integer id1 )
	{
		games.remove( id1 );
		logger.debug( id1 + " removed." );
	}

	/************************** game match ***********************************/
	public void addResult4Match1( Integer id1, DeferredResult<String> result )
	{
		results4match1.put( id1, result );
		getMatchDeferredResults1( id1, result );
	}

	public void addResult4Match2( Integer id2, DeferredResult<String> result )
	{
		results4match2.put( id2, result );
		getMatchDeferredResults2( id2, result );
	}

	// users is player 1, looking for player 2 request
	public void getMatchDeferredResults1( Integer id1,
			DeferredResult<String> result )
	{
		String json = "";
		StringWriter sw = new StringWriter();

		try
		{
			while ( !results4match1.isEmpty() )
			{
				for ( Integer id : games.keySet() )
				{
					if ( id == id1 )
					{

						if ( results4match2 != null
								&& !results4match2.isEmpty() )
						{

							for ( Integer id2 : results4match2.keySet() )
							{
								games.get( id1 ).setPlayer2(
										userDao.getUser( id2 ) );
								games.get( id1 ).setStartDate( new Date() );

								String s1 = new String(
										games.get( id1 ).getPlayer2()
												.getUsername()
												+ " has joined the game. Please make your move." );

								String s2 = new String(
										"Joined game hosted by "
												+ games.get( id1 ).getPlayer1()
														.getUsername()
												+ ". Waiting for "
												+ games.get( id1 ).getPlayer1()
														.getUsername()
												+ "\'s move ..." );

								System.out.println( "2+1" + s1 );
								System.out.println( "2+1" + s2 );

								System.out.println( id );
								System.out.println( games.get( id ) );

								objectMapper.writeValue( sw, games.get( id1 ) );
								System.out.println( sw );

								json = sw.toString();

								System.out.println( json );

								results4match1.remove( id1 ).setResult( json );
								results4match2.remove( id2 ).setResult( json );

							}
						}
					}
				}
			}

		} catch ( Exception e )
		{
			logger.error( "Failed to write to JSON", e );
		}
	}

	// users is player 2, looking for game and player 1
	public void getMatchDeferredResults2( Integer id2,
			DeferredResult<String> result )
	{
		String json = "";
		StringWriter sw = new StringWriter();

		try
		{
			while ( !results4match2.isEmpty() )
			{
				for ( Integer id : games.keySet() )
				{

					if ( games.get( id ).getStartDate() == null
							|| games.get( id ).getStartDate().equals( "" ) )
					{

						Integer id1 = id;
						games.get( id ).setPlayer2( userDao.getUser( id2 ) );
						games.get( id ).setStartDate( new Date() );

						String s1 = new String(
								games.get( id ).getPlayer2().getUsername()
										+ " has joined the game. Please make your move." );

						String s2 = new String( "Joined game hosted by "
								+ games.get( id ).getPlayer1().getUsername()
								+ ". Waiting for "
								+ games.get( id ).getPlayer1().getUsername()
								+ "\'s move ..." );

						System.out.println( "1+2" + s1 );
						System.out.println( "1+2" + s2 );

						System.out.println( id );
						System.out.println( games.get( id ) );

						objectMapper.writeValue( sw, games.get( id ) );
						System.out.println( sw );

						json = sw.toString();

						System.out.println( json );

						results4match1.remove( id1 ).setResult( json );
						results4match2.remove( id2 ).setResult( json );

					}

				}

			}
		} catch ( Exception e )
		{
			logger.error( "Failed to write to JSON", e );
		}
	}

	/************************** game proceed ***********************************/
	public void addResult( Integer id, DeferredResult<String> result )
	{
		results.put( id, result );
	}

	public void addMove1( Integer id1, Integer boardid )
	{
		for ( Integer id : games.keySet() )
		{
			if ( id == id1 )
			{
				// player 1 turn
				if ( games.get( id ).isTurn() )
				{
					games.get( id ).getBoard().set( boardid, 1 );

					// check if player1 or tie
					if ( games.get( id ).getOutcome() != null
							&& !games.get( id ).getOutcome().equals( "" ) )
					{
						games.get( id ).setEndDate( new Date() );
						Integer i = Integer.valueOf(id.intValue());
						games.put( i, gameDao.setGame( games.get( id ) ) );
						id = i;
					}

					games.get( id ).setTurn( false );

					System.out.println( "addMove1" );

					// proceed with DeferredResults---results
					processDeferredResults( id );
				}
			}
		}
	}

	public void addMove2( Integer id2, Integer boardid )
	{
		System.out.println( id2 );

		for ( Integer id : games.keySet() )
		{

			if ( games.get( id ).getPlayer2Id() == id2 )
			{
				// player 2 turn
				if ( !games.get( id ).isTurn() )
				{
					games.get( id ).getBoard().set( boardid, 2 );

					// check if finished or tie
					if ( games.get( id ).getOutcome() != null
							&& !games.get( id ).getOutcome().equals( "" ) )
					{
						games.get( id ).setEndDate( new Date() );
						Integer i = Integer.valueOf(id.intValue());
						games.put( i, gameDao.setGame( games.get( id ) ) );
						id = i;
					}

					games.get( id ).setTurn( true );

					System.out.println( "addMove2" );

					// proceed with DeferredResults---results
					processDeferredResults( id );
				}
			}
		}
	}

	// argument Integer is game host id
	public void processDeferredResults( Integer id )
	{
		String json = "";
		Integer id2 = 0;

		try
		{
			StringWriter sw = new StringWriter();

			for ( Integer id1 : games.keySet() )
			{
				if ( id == id1 )
				{
					objectMapper.writeValue( sw, games.get( id ) );
					json = sw.toString();
					id2 = games.get( id ).getPlayer2Id();

				}
			}
		} catch ( Exception e )
		{
			logger.error( "Failed to write to JSON", e );
		}

		for ( Integer i : results.keySet() )
		{
			System.out.println( "results.keySet() : " + i );
		}

		// process queued results
		while ( !results.isEmpty() )
		{
			results.remove( id ).setResult( json );
			results.remove( id2 ).setResult( json );
		}
	}

}
