package ttt.model.dao;

import java.util.List;

import ttt.model.Game;
import ttt.model.User;


public interface GameDao {
	// Retrieve all the completed games played by a user against the AI players
		// List<Game> getGamesAgainstAI( User user )
		List<Game> getGamesAgainstAI( User user );

		// Retrieve all the saved games by a user
		// List<Game> getSavedGames( User user )
		List<Game> getSavedGames( User user );
		
		Game setGame(Game game);

		Game getGame( Integer id );

		List<Game> getGamesAsPlayer1AgainstHuman( User user );
		
		List<Game> getGamesAsPlayer2AgainstHuman( User user );
}
