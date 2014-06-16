package ttt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    private Integer id;

    //for AI, username is "YYY"
    @Column(nullable = false, unique=true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @OneToMany(mappedBy="player1", cascade=CascadeType.ALL)
 	private Set<Game> gamesAsPlayer1;
    
    @OneToMany(mappedBy="player2", cascade=CascadeType.ALL)
 	private Set<Game> gamesAsPlayer2;
    
	@JsonIgnore
    @ElementCollection
    @CollectionTable(name = "authorities",
        joinColumns = @JoinColumn(name = "username", referencedColumnName="username"))
    @Column(name = "authority")
    private Set<String> roles;
    
	@JsonIgnore
    private boolean enabled = true;
    
    public User()
    {
    	roles = new HashSet<String>();
    	roles.add( "ROLE_USER" );
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
	
	public Set<String> getRoles()
	{
		return roles;
	}

	public void setRoles( Set<String> roles )
	{
		this.roles = roles;
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