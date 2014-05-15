package ttt.notinuse.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ttt.model.Game;

@Entity
@Table(name = "users")
public class UserReference implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    private Integer id;

    //for AI, username is "YYY"
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @OneToMany(mappedBy="player1", cascade=CascadeType.ALL)
 	private Set<Game> gamesAsPlayer1;
    
    @OneToMany(mappedBy="player2", cascade=CascadeType.ALL)
 	private Set<Game> gamesAsPlayer2;
    
    private boolean enabled;
    
    public UserReference()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }
	
	public String getEmail()
	{
		return email;
	}

	
	public void setEmail( String email )
	{
		this.email = email;
	}

	
	public Set<Game> getGamesAsPlayer1()
	{
		return gamesAsPlayer1;
	}

	
	public void setGamesAsPlayer1( Set<Game> gamesAsPlayer1 )
	{
		this.gamesAsPlayer1 = gamesAsPlayer1;
	}

	
	public Set<Game> getGamesAsPlayer2()
	{
		return gamesAsPlayer2;
	}

	
	public void setGamesAsPlayer2( Set<Game> gamesAsPlayer2 )
	{
		this.gamesAsPlayer2 = gamesAsPlayer2;
	}

	
	public boolean isEnabled()
	{
		return enabled;
	}

	
	public void setEnabled( boolean enabled )
	{
		this.enabled = enabled;
	}	

}