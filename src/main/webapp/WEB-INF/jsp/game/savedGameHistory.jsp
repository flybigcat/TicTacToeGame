<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>${player.username}</title>
<link href="../css/min.css" rel="stylesheet" />
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
                    <li><a href="/tttGame/user/logout.html">Logout</a></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    
	<h3>${player.username}'s  saved  Games:</h3>

	<c:if test="${savedGames == null || fn:length(savedGames) == 0}">
           There is no game saved.<br />
	</c:if>

	<c:if test="${savedGames != null && fn:length(savedGames) != 0}">
		<table border="1">
			<tr>
				<th>start date</th>
				<th>saved date</th>
				<th>operation</th>
			</tr>


			<c:forEach items="${savedGames}" var="game">
				<tr>
					<td>${game.startDate}</td>
					<td>${game.saveDate}</td>
					<td><a href="TicTacToeGame.html?resumeGameID=${game.id}">Resume</a></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>
      
</body>
</html>