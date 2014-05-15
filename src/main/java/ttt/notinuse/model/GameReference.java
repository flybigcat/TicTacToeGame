package ttt.notinuse.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ttt.model.User;

@Entity
@Table(name = "games")
public class GameReference implements Serializable {

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
	private Outcome outcome;
	
	enum Outcome{ONGOING, WIN, LOSS, TIE};
	
	@Transient
	private int board[][];
	
	private String boardString;

	
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

	
	public Outcome getOutcome()
	{
		return outcome;
	}

	
	public void setOutcome( Outcome outcome )
	{
		this.outcome = outcome;
	}

	
	public int[][] getBoard()
	{
		return board;
	}

	
	public void setBoard( int[][] board )
	{
		this.board = board;
	}

	
	public String getBoardString()
	{
		return boardString;
	}

	
	public void setBoardString( String boardString )
	{
		this.boardString = boardString;
	}
	

}
