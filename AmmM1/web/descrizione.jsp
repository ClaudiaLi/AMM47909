<%-- 
    Document   : descrizione
    Created on : 29-apr-2016, 18.22.58
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
            <h1 class="titolo1" >Come Nuovo</h1>
            <nav>
                <ul>
                    <li><a href="#descrizione"> Descrizione generale </a></li>
                    <li><a href="#funzioni"> Funzioni principali </a></li>
                    <li>
                    <!-- Se è stato effettuato il login da un utente allora comparirà il link di logout-->
                        <c:if test="${sessionScope.utente != null}">
                            <div id="logout"><a href="logout.html">Logout</a></div>
                        </c:if>
                    </li>
                    <li id="login"><a href="login.html">Login</a></li>
                </ul>
            </nav>
        </header>
        </div>
            <jsp:include page="sidebar.jsp" />
                
        <div id="content_bar">
            <h1 class="titolo" >Benvenuto!</h1>
            <a id="descrizione"><h2 class="titolo">Descrizione generale</h2></a>
            <p>Benvenuto nel nostro negozio di articoli usati!</p>
            <p>Con le tue credenziali potrai acquistare articoli di qualunque genere a prezzi stracciati!</p>
            <a id="funzioni"><h2 class="titolo">Funzioni principali</h2></a>
            <p>Grazie al nostro sito sar&agrave; semplicissimo acquistare o vendere qualunque tipo di oggetto usato!<br/><br/>
                Ottieni subito le credenziali e inizia la compravendita pi&ugrave; veloce che ci sia!</p>
            <h3 class="titolo">Se vuoi vendere...</h3>
            <p>Potrai visualizzare la tua lista di oggetti in vendita. <br/><br/>
                Potrai aggiungere gli articoli che vuoi vendere in modo semplice e veloce. <br/><br/>
                Potrai modificare i dati relativi agli articoli che hai gi&agrave; messo in vendita. <br/><br/>
                Potrai eliminare gli articoli che non vuoi pi&ugrave; vendere con un semplice click!
            </p> 
            <h3 class="titolo">Se vuoi acquistare...</h3> 
            <p>Avrai a disposizione gli articoli di tutti i venditori di articoli usati presenti! <br/><br/>
                Avrai la possibilit&agrave; di acquistare con un semplice click ci&ograve; che di pi&ugrave; ti interessa, <br/><br/>
                e di ripensarci grazie al riepilogo se non ti interessa pi&ugrave;!
            </p>
        </div>
            
         <jsp:include page="footer.jsp" />
        
        </div>
    </body>
</html>
