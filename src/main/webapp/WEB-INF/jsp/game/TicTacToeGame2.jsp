<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../css/min.css" rel="stylesheet" />
<style>
table,th,td
{
border:8px solid black;
font-size:80px;
}
</style>
<title>Tic Tac Toe</title>
<script type="text/javascript" src="../js/jquery-2.1.1.min.js"></script>

<c:if test="${playerRole ==1 }">
	<script type="text/javascript">
		//this is call back function
		$(function() {
			$.ajax({
				url : "TicTacToeGame1.json",
				cache : false,
				success : function(data) {
					$("#table").empty();
					$("#player1waiting").html(data.player1waiting);

				},
			});
			startup1();
		});

		function startup1() {
			$
					.ajax({
						url : "TicTacToeGame1.deferred.json",
						cache : false,
						success : function(data) {
							$("#player1waiting").empty();
							$("#player1waiting")
									.html(
											data.player2Username
													+ " has joined the game. Please make your move.");

							for (var i = 0; i < 9; i = i + 3) {
								var c1, c2, c3;
								c1 = ("<tr><td boardid="+i+">&nbsp;&nbsp;&nbsp;</td>");
								c2 = ("<td boardid=" + (i + 1) + ">&nbsp;&nbsp;&nbsp;</td>");
								c3 = ("<td boardid=" + (i + 2) + ">&nbsp;&nbsp;&nbsp;</td></tr>");
								$("#table").append(c1 + c2 + c3);
							}

							move1();
							update1();
						},
					});
		}

		function move1() {
			var boardid = -1;
			$("td").click(function() {
				boardid = $(this).attr("boardid");
				if (boardid != -1) {
					$.ajax({
						url : "move1.html?&boardid=" + boardid,
					});
				}
			});

		}

		function update1() {
			$
					.ajax({
						url : "TicTacToeGame.deferred.json",
						cache : false,
						success : function(data) {
							$("#player1waiting").empty();

							if (data.outcome != null && data.outcome != ("")) {
								$("#welcome").html("<a href='../welcome.jsp'>Go back to Welcome page</a>");
                                $("#logout").html("<a href='/ttt/user/logout.html'>Logout</a>");
                                
								if (data.outcome == ("win"))
									$("#player1waiting").html("You win!");
								if (data.outcome == ("loss"))
									$("#player1waiting").html("You loss!");
								if (data.outcome == ("tie"))
									$("#player1waiting").html("Game tie!");
							}

							if (data.outcome == ("")){
								if( data.turn == true) $("#player1waiting").html("Make your move");
								if( data.turn == false) $("#player1waiting").html("Please wait for "+data.player2Username+"\' move.");
							}
								

							$("#table").empty();
							for (var i = 0; i < 9; i = i + 3) {
								var c1, c2, c3;
								if (data.board[i] == 0)
									c1 = ("<tr><td boardid="+i+">&nbsp;&nbsp;&nbsp;</td>");
								if (data.board[i] == 1)
									c1 = ("<tr><td style='color: rgb(0, 0, 255)'>X</td>");
								if (data.board[i] == 2)
									c1 = ("<tr><td style='color: rgb(255, 0, 0)'>O</td>");

								if (data.board[i + 1] == 0)
									c2 = ("<td boardid=" + (i + 1) + ">&nbsp;&nbsp;&nbsp;</td>");
								if (data.board[i + 1] == 1)
									c2 = ("<td style='color: rgb(0, 0, 255)'>X</td>");
								if (data.board[i + 1] == 2)
									c2 = ("<td style='color: rgb(255, 0, 0)'>O</td>");

								if (data.board[i + 2] == 0)
									c3 = ("<td boardid=" + (i + 2) + ">&nbsp;&nbsp;&nbsp;</td></tr>");
								if (data.board[i + 2] == 1)
									c3 = ("<td style='color: rgb(0, 0, 255)'>X</td></tr>");
								if (data.board[i + 2] == 2)
									c3 = ("<td style='color: rgb(255, 0, 0)'>O</td></tr>");
								$("#table").append(c1 + c2 + c3);
							}

							if (data.outcome == null || data.outcome == ("")) {
								move1();
								update1();
							}
						},
					});

		}
	</script>
</c:if>

<c:if test="${playerRole ==2 }">
	<script type="text/javascript">
		//this is call back function
		$(function() {
			$.ajax({
				url : "TicTacToeGame2.json",
				cache : false,
				success : function(data) {
					$("#table").empty();
					$("#player2waiting").html(data.player2waiting);

				},
			});
			startup2();
		});

		function startup2() {
			$
					.ajax({
						url : "TicTacToeGame2.deferred.json",
						cache : false,
						success : function(data) {
							$("#player2waiting").empty();
							$("#player2waiting").html(
									"Joined game hosted by "
											+ data.player1Username
											+ ". Waiting for "
											+ data.player1Username + "'s move");
							for (var i = 0; i < 9; i = i + 3) {
								var c1, c2, c3;
								c1 = ("<tr><td boardid="+i+">&nbsp;&nbsp;&nbsp;</td>");
								c2 = ("<td boardid=" + (i + 1) + ">&nbsp;&nbsp;&nbsp;</td>");
								c3 = ("<td boardid=" + (i + 2) + ">&nbsp;&nbsp;&nbsp;</td></tr>");
								$("#table").append(c1 + c2 + c3);
							}

							move2();
							update2();
						},
					});
		}

		function move2() {
			var boardid = -1;
			$("td").click(function() {
				boardid = $(this).attr("boardid");
				if (boardid != -1) {
					$.ajax({
						url : "move2.html?&boardid=" + boardid,
					});
				}
			});

		}

		function update2() {
			$
					.ajax({
						url : "TicTacToeGame.deferred.json",
						cache : false,
						success : function(data) {
							$("#player2waiting").empty();

							if (data.outcome != null && data.outcome != "") {
								$("#welcome").html("<a href='../welcome.jsp'>Go back to Welcome page</a>");
							    $("#logout").html("<a href='/ttt/user/logout.html'>Logout</a>");
								
							    if (data.outcome == ("win"))
									$("#player2waiting").html("You loss!");
								if (data.outcome == ("loss"))
									$("#player2waiting").html("You win!");
								if (data.outcome == ("tie"))
									$("#player2waiting").html("Game tie!");
							}

							if (data.outcome == ("")){
                                if( data.turn == false) $("#player2waiting").html("Make your move");
                                if( data.turn == true) $("#player2waiting").html("Please wait for "+data.player1Username+"\' move.");
                            }

							$("#table").empty();
							for (var i = 0; i < 9; i = i + 3) {
								var c1, c2, c3;
								if (data.board[i] == 0)
									c1 = ("<tr><td boardid="+i+">&nbsp;&nbsp;&nbsp;</td>");
								if (data.board[i] == 1)
									c1 = ("<tr><td style='color: rgb(0, 0, 255)'>X</td>");
								if (data.board[i] == 2)
									c1 = ("<tr><td style='color: rgb(255, 0, 0)'>O</td>");

								if (data.board[i + 1] == 0)
									c2 = ("<td boardid=" + (i + 1) + ">&nbsp;&nbsp;&nbsp;</td>");
								if (data.board[i + 1] == 1)
									c2 = ("<td style='color: rgb(0, 0, 255)'>X</td>");
								if (data.board[i + 1] == 2)
									c2 = ("<td style='color: rgb(255, 0, 0)'>O</td>");

								if (data.board[i + 2] == 0)
									c3 = ("<td boardid=" + (i + 2) + ">&nbsp;&nbsp;&nbsp;</td></tr>");
								if (data.board[i + 2] == 1)
									c3 = ("<td style='color: rgb(0, 0, 255)'>X</td></tr>");
								if (data.board[i + 2] == 2)
									c3 = ("<td style='color: rgb(255, 0, 0)'>O</td></tr>");

								$("#table").append(c1 + c2 + c3);
							}

							if (data.outcome == null || data.outcome == ("")) {
								move2();
								update2();
							}
						},
					});
		}
	</script>
</c:if>

</head>

<body>
	<c:if test="${playerRole ==1 }">
		<h3>
			<span id="player1waiting"></span>
		</h3>
	</c:if>

	<c:if test="${playerRole ==2 }">
		<h3>
			<span id="player2waiting"></span>
		</h3>
	</c:if>
	
	<h3 id="welcome"></h3>
    <h3 id="logout"></h3>

<div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle">
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" >Tic Tac Toe Game</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                     
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>

     <div class="container">
        <h1>Tic Tac Toe Game</h1>
    </div>


	<table style="width:300px; height:300px" id="table">
	</table>


</body>
</html>