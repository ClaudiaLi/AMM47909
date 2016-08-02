<%-- 
    Document   : accessoNegato
    Created on : 10-lug-2016, 17.02.25
    Author     : utente
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Come Nuovo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Claudia Licheri">
        <meta name="description" content="ecommerce usato">
        <meta name="keywords" content="html, css, java, usato, sardegna, ecommerce">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>
    <body>
        <div id="page">
            
            <div id="header">
            <header>
                <h1 class="titolo1">Come Nuovo</h1>
                <nav>
                    <ul>
                        <li id="login"><a href="login.html">Login</a></li>
                        <li><a href="descrizione.html">Descrizione</a></li>
                        <li>
                            <c:if test="${sessionScope.utente != null}">
                                <div id="logout"><a href="logout.html">Logout</a></div>
                            </c:if>
                        </li>
                    </ul>
                </nav>
            </header>
            </div>
            
            <jsp:include page="sidebar.jsp" />
            <div class="denied">
                <h3>Accesso negato!</h3>
                <p>Non sei autorizzato ad accedere a questa pagina! </p>
                <p>Prova rieffettuando il login. </p>
            </div>
            
            <jsp:include page="footer.jsp" />
            
        </div>
    </body>
</html>
 