package ttt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private User player1;

	@ManyToOne
	private User player2;

	private Date startDate;

	// if null, it is not finished, saved game by user, not
	private Date endDate;

	// if null, it is finished
	private Date saveDate;

	// if play1 win, it is "win", player2 win, "loss", or "tie"
	// or just empty"" for incomplete
	private String outcome;

	@ElementCollection
	@CollectionTable(name = "game_boards", joinColumns = @JoinColumn(name = "game_id"))
	@Column(name = "boardCell")
	// By default, TopLink JPA retrieves the members of a Collection association
	// in ascending order by primary key of the associated entities
	@OrderColumn(name = "board_order")
	private List<Integer> board;

	public Game()
	{
		outcome = new String();

		board = new ArrayList<Integer>();

		// beginning is all 0
		for ( int i = 0; i < 9; i++ )
		{
			board.add( 0 );
		}

	}

	/******************** get next step **************************/
	// Min + (int)(Math.random() * ((Max - Min) + 1))
	// (Min,Max)
	public int getRandomStep()
	{
		int randomS = -1;
		if ( board != null && board.size() == 9 )
		{
			do
			{
				randomS = (int) ( Math.random() * 9 );
			} while ( board.get( randomS ) != 0 );

		}
		return randomS;
	}

	// set p1WS position, if player 1 is going to win
	public int getP1WinStep()
	{
		int p1WS = -1;

		if ( board != null && board.size() == 9 )
		{
			// horizontal
			// horizontal
			for ( int row = 0; row < 3; row++ )
			{
				int r = 0;

				for ( int col = 0; col < 3; col++ )
				{
					if ( board.get( 3 * row + col ) == 1 )
						r++;
				}

				if ( r == 2 )
				{
					for ( int col = 0; col < 3; col++ )
					{
						if ( board.get( 3 * row + col ) == 0 )
						{
							p1WS = 3 * row + col;
						}
					}
				}
			}

			// vertical
			for ( int col = 0; col < 3; col++ )
			{
				int c = 0;

				for ( int row = 0; row < 3; row++ )
				{
					if ( board.get( 3 * row + col ) == 1 )
						c++;
				}

				if ( c == 2 )
				{
					for ( int row = 0; row < 3; row++ )
					{
						if ( board.get( 3 * row + col ) == 0 )
						{
							p1WS = 3 * row + col;
						}
					}
				}
			}

			// diagonal1
			int d1 = 0;

			for ( int diagonal = 0; diagonal < 3; diagonal++ )
			{
				if ( board.get( 3 * diagonal + diagonal ) == 1 )
					d1++;
			}

			if ( d1 == 2 )
			{
				for ( int diagonal = 0; diagonal < 3; diagonal++ )
				{
					if ( board.get( 3 * diagonal + diagonal ) == 0 )
						p1WS = 3 * diagonal + diagonal;
				}
			}

			// the other diagonal
			if ( board.get( 6 ) == 1 && board.get( 4 ) == 1
					&& board.get( 2 ) == 0 )
			{
				p1WS = 2;
			}
			if ( board.get( 6 ) == 1 && board.get( 4 ) == 0
					&& board.get( 2 ) == 1 )
			{
				p1WS = 4;
			}
			if ( board.get( 6 ) == 0 && board.get( 4 ) == 1
					&& board.get( 2 ) == 1 )
			{
				p1WS = 6;
			}
		}
		return p1WS;
	}

	// player 2
	public int getP2WinStep()
	{
		int p2WS = -1;
		if ( board != null && board.size() == 9 )
		{
			// horizontal
			// horizontal
			for ( int row = 0; row < 3; row++ )
			{
				int r = 0;

				for ( int col = 0; col < 3; col++ )
				{
					if ( board.get( 3 * row + col ) == 2 )
						r++;
				}

				if ( r == 2 )
				{
					for ( int col = 0; col < 3; col++ )
					{
						if ( board.get( 3 * row + col ) == 0 )
						{
							p2WS = 3 * row + col;
						}
					}
				}
			}

			// vertical
			for ( int col = 0; col < 3; col++ )
			{
				int c = 0;

				for ( int row = 0; row < 3; row++ )
				{
					if ( board.get( 3 * row + col ) == 2 )
						c++;
				}

				if ( c == 2 )
				{
					for ( int row = 0; row < 3; row++ )
					{
						if ( board.get( 3 * row + col ) == 0 )
						{
							p2WS = 3 * row + col;
						}
					}
				}
			}

			// diagonal1
			int d1 = 0;

			for ( int diagonal = 0; diagonal < 3; diagonal++ )
			{
				if ( board.get( 3 * diagonal + diagonal ) == 2 )
					d1++;
			}

			if ( d1 == 2 )
			{
				for ( int diagonal = 0; diagonal < 3; diagonal++ )
				{
					if ( board.get( 3 * diagonal + diagonal ) == 0 )
						p2WS = 3 * diagonal + diagonal;
				}
			}

			// the other diagonal
			if ( board.get( 6 ) == 2 && board.get( 4 ) == 2
					&& board.get( 2 ) == 0 )
			{
				p2WS = 2;
			}
			if ( board.get( 6 ) == 2 && board.get( 4 ) == 0
					&& board.get( 2 ) == 2 )
			{
				p2WS = 4;
			}
			if ( board.get( 6 ) == 0 && board.get( 4 ) == 2
					&& board.get( 2 ) == 2 )
			{
				p2WS = 6;
			}
		}
		return p2WS;
	}

	/********************* isTied, isP1Won, isP2Won **********************/
	public String getOutcome()
	{
		if ( board != null && board.size() == 9 )
		{
			// tie
			boolean tied = true;
			for ( int row = 0; row < 3; row++ )
			{
				for ( int col = 0; col < 3; col++ )
				{
					if ( board.get( 3 * row + col ) == 0 )
						tied = false;
				}
			}
			if ( tied )
				outcome = "tie";

			// player1 win
			boolean p1Won = false;

			// horizontal
			for ( int row = 0; row < 3; row++ )
			{
				int r = 0;

				for ( int col = 0; col < 3; col++ )
				{
					if ( board.get( 3 * row + col ) == 1 )
						r++;
				}

				if ( r == 3 )
					p1Won = true;
			}

			// vertical
			for ( int col = 0; col < 3; col++ )
			{
				int c = 0;
				for ( int row = 0; row < 3; row++ )
				{
					if ( board.get( 3 * row + col ) == 1 )
						c++;
				}

				if ( c == 3 )
					p1Won = true;
			}

			// diagonal
			int d1 = 0;
			for ( int diagonal = 0; diagonal < 3; diagonal++ )
			{
				if ( board.get( 3 * diagonal + diagonal ) == 1 )
					d1++;
			}
			if ( d1 == 3 )
				p1Won = true;

			// the other diagonal
			if ( board.get( 6 ) == 1 && board.get( 4 ) == 1
					&& board.get( 2 ) == 1 )
				p1Won = true;

			if ( p1Won )
				outcome = "win";

			// player1 win
			boolean p2Won = false;

			// horizontal
			for ( int row = 0; row < 3; row++ )
			{
				int r = 0;

				for ( int col = 0; col < 3; col++ )
				{
					if ( board.get( 3 * row + col ) == 2 )
						r++;
				}

				if ( r == 3 )
					p2Won = true;
			}

			// vertical
			for ( int col = 0; col < 3; col++ )
			{
				int c = 0;
				for ( int row = 0; row < 3; row++ )
				{
					if ( board.get( 3 * row + col ) == 2 )
						c++;
				}

				if ( c == 3 )
					p2Won = true;
			}

			// diagonal
			int d2 = 0;
			for ( int diagonal = 0; diagonal < 3; diagonal++ )
			{
				if ( board.get( 3 * diagonal + diagonal ) == 2 )
					d2++;
			}
			if ( d2 == 3 )
				p2Won = true;

			// the other diagonal
			if ( board.get( 6 ) == 2 && board.get( 4 ) == 2
					&& board.get( 2 ) == 2 )
				p2Won = true;

			if ( p2Won )
				outcome = "loss";
		}

		// if(outcome.equals( "win" )||outcome.equals( "loss" )||outcome.equals(
		// "tie" ))
		return outcome;

	}

	/********************** system generated getters and setters ****************/

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public User getPlayer1()
	{
		return player1;
	}

	public void setPlayer1( User player1 )
	{
		this.player1 = player1;
	}

	public User getPlayer2()
	{
		return player2;
	}

	public void setPlayer2( User player2 )
	{
		this.player2 = player2;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

	public Date getSaveDate()
	{
		return saveDate;
	}

	public void setSaveDate( Date saveDate )
	{
		this.saveDate = saveDate;
	}

	public List<Integer> getBoard()
	{
		return board;
	}

	public void setBoard( List<Integer> board )
	{
		this.board = board;
	}

	public void setOutcome( String outcome )
	{
		this.outcome = outcome;
	}

}
