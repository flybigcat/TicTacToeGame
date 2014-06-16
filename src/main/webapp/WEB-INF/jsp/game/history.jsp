<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../css/min.css" rel="stylesheet" />
<title>${player.username}</title>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle">
                    <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../">Welcome to Tic Tac Toe Game</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">

                    <li><a href="../welcome.jsp">Go back to Welcome page</a></li>
                    <li><a href="/ttt/user/logout.html">Logout</a></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    
	<h3>${player.username}'s Game History:</h3>
	<ul>
		<li>The total number of games completed is ${totalN}</li>
		<li>The number of 1-player games completed is ${OnePlayerN}</li>
		<li>The number of 2-player games completed is ${TwoPlayersN}</li>

		<c:if test="${winAI == -1}">
			<li>The number of 1-player games completed is 0, so the win rate
				against AI can not be calculated. Or you can take it as 0.0%</li>
		</c:if>
		<c:if test="${winAI != -1}">
			<li>The win rate against AI is ${winAI * 100}%</li>
		</c:if>

		<c:if test="${winHuman == -1}">
			<li>The number of 2-player games completed is 0, so the win rate
				against human can not be calculated. Or you can take it as 0.0%</li>
		</c:if>
		<c:if test="${winHuman != -1}">
			<li>The win rate against human players is ${winHuman*100}%</li>
		</c:if>

		<li>The list of games played this month:</li>
		<c:if
			test="${gameAIMonth == null && gamePlayer1Month ==null && gamePlayer2Month == null}">
           There is no game played this month.<br />
		</c:if>

		<c:if
			test="${gameAIMonth != null || gamePlayer1Month !=null || gamePlayer2Month != null}">
			<table border="1">
				<tr>
					<th>Opponent's name</th>
					<th>Game Length / Seconds</th>
					<th>Outcome</th>
				</tr>

				<c:if test="${gameAIMonth != null}">
					<c:forEach items="${gameAIMonth}" var="game">
						<tr>
							<td>AI</td>
							<td>${game.endDate.time/1000 - game.startDate.time/1000}</td>
							<td>${game.outcome}</td>
						</tr>
					</c:forEach>
				</c:if>

				<c:if test="${gamePlayer1Month != null}">
					<c:forEach items="${gamePlayer1Month}" var="game">
						<tr>
							<td>${game.player2.username}</td>
							<td>${game.endDate.time/1000 - game.startDate.time/1000}</td>
							<td>${game.outcome}</td>
						</tr>
					</c:forEach>
				</c:if>

				<c:if test="${gamePlayer2Month != null}">
					<c:forEach items="${gamePlayer2Month}" var="game">
						<tr>
							<td>${game.player1.username}</td>
							<td>${game.endDate.time/1000 - game.startDate.time/1000}</td>
							<c:if test="${game.outcome == 'win'}">
								<td>loss</td>
							</c:if>
							<c:if test="${game.outcome == 'loss'}">
								<td>win</td>
							</c:if>
							<c:if test="${game.outcome == 'tie'}">
								<td>tie</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>


			</table>
		</c:if>

	</ul>

</body>
</html>