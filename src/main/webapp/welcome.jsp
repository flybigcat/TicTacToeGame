<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="css/min.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('#clickBtn').click(function() {
            
            var random = parseInt(10*Math.random());
            
            var luck;
            
            if (random > 3) {
                luck = "Perfect";
            }
            if (random === 3) {
                luck = "Great";
            } 
            if (random < 3) {
                luck = "Good";
            }
            $('#number').html(luck);
        });
    });
</script>
<title>Tic Tac Toe Game</title>
</head>

<body>
    <div class="container">
        <h1>Tic Tac Toe Game</h1>
    </div>
    
	<security:authorize access="not authenticated">
		<h3>
			<a href="login.jsp">Login</a>
		</h3>
		<h3>
			<a href="user/registration.html">Registration</a>
		</h3>
	</security:authorize>


	<h3>
		<security:authorize access="authenticated">
			<a href="user/logout.html">Logout</a>
		</security:authorize>
	</h3>

	   <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle">
                    <span class="icon-bar"></span> 
                    <span class="icon-bar"></span> 
                    <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                
                </button>
                <a class="navbar-brand" href="">Welcome to Tic Tac Toe Game</a>
            </div>
            
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="">Home</a></li>
                    <li><a href="game/TicTacToeGame.html">Game</a></li>
                    <li><a href="game/TicTacToeGame2.html?playerRole=1">Host a Game</a></li>
                    <li><a href="game/TicTacToeGame2.html?playerRole=2">Join a Game</a></li>
                    <li><a href="game/history.html">History</a></li>
                    <li><a href="game/savedGameHistory.html">Saved Games List</a></li>
                    <li><button id="clickBtn">
                            Try Your Luck:  <span id="number">Hello</span>
                        </button></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>




    <p>
        <img align="middle" src="images/tttbackground1.jpg" />
    </p>
</body>
</html>