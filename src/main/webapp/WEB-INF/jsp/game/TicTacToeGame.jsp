<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../css/min.css" rel="stylesheet" />
<style>
table,th,td
{
border:8px solid black;
}
</style>
<title>Tic Tac Toe</title>
</head>
<body>
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

                    <c:if test="${not empty end}">
                        <li><a href="../welcome.jsp">Go back to Welcome page</a></li>
                    </c:if>
                    <li><a href="TicTacToeGame.html?newGame=1">New Game</a></li>
                   
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>

     <div class="container">
        <h1>Tic Tac Toe Game</h1>
    </div>

    <c:if test="${empty end}">
    <h3>
     <security:authorize access="authenticated">
           <a href="saveGame.html">Save this Game for later Resume</a>
     </security:authorize>        
    </h3>
    </c:if>


    <h3>
     <security:authorize access="authenticated">
        <a href="../user/logout.html">Logout</a>
     </security:authorize>  
    </h3>
	<h2>
		<c:if test="${empty end}">
Please make your move:
</c:if>

		<c:if test="${not empty end}">
			<c:if test="${end eq 1}">
You won!
</c:if>
			<c:if test="${end eq 0}">
Game tied!
</c:if>
			<c:if test="${end eq 2}">
I won!
</c:if>
		</c:if>
	</h2>

	<table style="width:300px; height:300px">

		<c:forEach begin="0" end="2" step="1" var="row">
			<tr>
				<c:forEach begin="0" end="2" step="1" var="column">

					<c:if test="${empty end}">
						<c:if test="${tttGame.board[3*row+column] eq 0}">
							<td style="font-size:80px"><a href="TicTacToeGame.html?i=${3*row+column}">_</a></td>
						</c:if>
					</c:if>

					<c:if test="${not empty end}">
						<c:if test="${tttGame.board[3*row+column] eq 0}">
							<td style="font-size:80px">_</td>
						</c:if>
					</c:if>

					<c:if test="${tttGame.board[3*row+column] eq 1}">
						<td style="font-size:80px; color: rgb(0, 0, 255)">X</td>
					</c:if>

					<c:if test="${tttGame.board[3*row+column] eq 2}">
						<td style="font-size:80px; color: rgb(255, 0, 0)">O</td>
					</c:if>

				</c:forEach>

			</tr>
		</c:forEach>


	</table>
	


</body>
</html>