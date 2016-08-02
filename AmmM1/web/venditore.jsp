<%-- 
    Document   : venditore
    Created on : 29-apr-2016, 18.23.25
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
        <div id="header">
            <header>
                <h1 class="titolo1">Come Nuovo</h1>
                <nav>
                    <ul>
                        <li id="login"><a href="login.html">Login</a></li>
                        <li><a href="descrizione.html">Descrizione</a></li>
                        <li>
                            <!-- Se è stato effettuato il login da un utente allora comparirà il link di logout-->
                            <c:if test="${sessionScope.utente != null}">
                                <div id="logout"><a href="logout.html">Logout</a></div>
                            </c:if>
                        </li>
                    </ul>
                </nav>
            </header>
        </div>
            
        <jsp:include page="sidebar.jsp" />

        <div id="content">
            <h3>Benvenuto!</h3>
            <p> Seleziona la voce "Nuovo" per inserire un nuovo articolo in vendita e compila i campi!<br/>
                Se vuoi modificare un articolo gi&aacute; presente, selezionalo e modifica i campi che ti interessa modificare.<br/>
                Se vuoi eliminare un articolo, selezionalo e clicca il tasto "Elimina"!
            </p>

            <c:if test="${listaSize == 0}">
                <p class="error">Non hai nessun articolo in vendita</p>
            </c:if>
                
            <c:if test="${errore==true}">
                <p class="error"> ${messaggioErrore} </p>
            </c:if>

            <c:if test="${elimina==true}">
                <p class="ok"> L'articolo selezionato &egrave; stato eliminato! </p>
            </c:if>

            <c:if test="${modifica==false}">
                <p class="ok"> Non &egrave; stata eseguita nessuna modifica!</p>
            </c:if>


            <div class="form" >
                <h3>Aggiungi un nuovo articolo!</h3>
                <form action="venditore.html" method="post">
                    <label for="idarticolo">Seleziona l'articolo</label> <br/>
                    <div class="item-select">
                    <select name="idarticolo" id="idarticolo">
                        <option value="Nuovo">Nuovo</option>
                        <c:forEach var="obj" items="${listaArticoli}" >
                            <option value="${obj.id}"> ${obj.nomeArticolo} </option>
                        </c:forEach>
                    </select>   
                    </div>

                    <label for="nomeArticolo">Nome articolo: </label>
                    <input type="text" name="nomeArticolo" id="nomeArticolo" value="${param["nomeArticolo"]}" />

                    <label for="urlimmagine">Inserisci l'immagine</label>
                    <input type="text" name="urlimmagine" id="urlimmagine" value="${param["urlimmagine"]}" placeholder="Immagini/..." />

                    <label for="descrizione">Inserisci la descrizione: </label>
                    <textarea rows="3" cols="15" name="descrizione" id="descrizione" value="${param["descrizione"]}" placeholder="..."></textarea>
                    <div class="spazio"></div>
                    <!-- L'attributo step permette di inserire numeri decimali -->
                    <label for="prezzo">Prezzo per unità</label>
                    <input type="number" step="0.01" min="0" name="prezzo" id="prezzo" value="${param["prezzo"]}">

                    <label for="quantita">Quantità</label>
                    <input type="number" name="quantita" id="quantita" min="0" value="${param["quantita"]}">

                    <div class="bottoni">
                        <button type="submit" value="Aggiungi" name="aggiungi">Aggiungi</button>
                        <!-- Se non ci sono articoli in vendita, questi non possono essere modificati o eliminati-->
                        <c:if test="${listaSize != 0}">
                            <button type="submit" value="Modifica" name="modifica">Modifica</button>
                            <button type="submit" value="Elimina" name="elimina">Elimina</button>                            
                        </c:if>
                        <button type="reset" value="Reset" id="reset">Reset</button>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="footer.jsp" />

    </body>
</html>
 