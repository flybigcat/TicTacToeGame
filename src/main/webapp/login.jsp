<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="css/min.css" rel="stylesheet" />
<title>Login Page</title>
</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle">
                    <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="welcome.jsp">Welcome to Tic Tac Toe Game</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">

                    <li><a href="welcome.jsp">Go back to Welcome page</a></li>
                    <li><a href="/ttt/user/registration.html">Registration</a></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    
<!-- special url -->
<form action="j_spring_security_check" method="post">
  Username: <input type="text" name="j_username" /> <br />
  Password: <input type="password" name="j_password" /> <br />
  <input type="submit" name="login" value="Login" />
</form>

</body>
</html>