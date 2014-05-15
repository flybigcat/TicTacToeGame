package ttt.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.GameDao;

@Repository
public class GameDaoImpl implements GameDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Game getGame( Integer id )
	{
		return entityManager.find( Game.class, id );
	}

	// Retrieve all the completed games played by a user against the AI players
	@Override
	public List<Game> getGamesAgainstAI( User user )
	{
		return entityManager
				.createQuery(
						"from Game where player1.id = :player1 and player2.id = 2 and outcome is not null and outcome !='' ",
						Game.class ).setParameter( "player1", user.getId() )
				.getResultList();
	}

	// Retrieve all the completed games played by a user as player1 against
	// human
	@Override
	public List<Game> getGamesAsPlayer1AgainstHuman( User user )
	{
		return entityManager
				.createQuery(
						"from Game where player1.id = :player1 and outcome is not null and outcome !='' and player2.id !=2 ",
						Game.class ).setParameter( "player1", user.getId() )
				.getResultList();
	}

	// Retrieve all the completed games played by a user as player2 against
	// human
	@Override
	public List<Game> getGamesAsPlayer2AgainstHuman( User user )
	{
		return entityManager
				.createQuery(
						"from Game where player2.id = :player2 and outcome is not null and outcome !=''",
						Game.class ).setParameter( "player2", user.getId() )
				.getResultList();
	}

	// Retrieve all the saved games by a user
	@Override
	public List<Game> getSavedGames( User user )
	{
		return entityManager
				.createQuery(
						"from Game where outcome is null and player1.id = :player and player2.id=2 and saveDate is not null "
						+ "or outcome = '' and player1.id = :player and player2.id=2 and saveDate is not null",
						Game.class ).setParameter( "player", user.getId() )
				.getResultList();
	}
	
	

	@Transactional
	@Override
	public Game setGame( Game game )
	{
		return game = entityManager.merge( game );
	}

}