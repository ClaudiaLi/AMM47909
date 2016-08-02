<%-- 
    Document   : login
    Created on : 29-apr-2016, 18.23.14
    Author     : utente
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Come Nuovo - login </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Claudia Licheri">
        <meta name="description" content="ecommerce usato">
        <meta name="keywords" content="html, css, java, usato, sardegna, ecommerce">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>
    <body>
        <header>
            <h1 class="titolo1" >Come Nuovo</h1>
            <nav>
                <ul>
                    <li><a href="descrizione.html">Descrizione</a></li>
                    <li><a href="cliente.html">Cliente</a></li>
                    <li><a href="venditore.html">Venditore</a></li>
                </ul>
            </nav>
        </header>

        <div id="content">
            <div class="form" id="login_form">
                <h3>Login</h3>
                <form action="login.html" method="post">

                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" value="" />
                    <label for="pswd">Password</label>
                    <input type="password" name="pswd" id="pswd" value="" />

                    <button type="submit" value="Login" name="submit">Accedi</button>

                </form>

                <c:if test="${errore == true}">
                    <p class="error">Accesso fallito!</p>
                </c:if>

            </div>
        </div>
        <jsp:include page="footer.jsp" />

    </body>
</html>

