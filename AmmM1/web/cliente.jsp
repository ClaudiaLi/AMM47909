<%-- 
    Document   : cliente
    Created on : 29-apr-2016, 18.22.43
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
        <script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="js/filter.js"></script>
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

        <h3>Articoli in vendita</h3>
                
        <!-- Se dopo la conferma di acquisto, il pagamento è andato a buon fine, viene comunicato al cliente-->
        <c:if test="${conferma==true}">
            <c:choose>
                <c:when test="${pagamento == true}" >
                    <p class="ok"> Pagamento avvenuto con successo! </p>
                </c:when>
                <c:otherwise>
                    <p class="error"> Il tuo credito è insufficiente! </p>
                </c:otherwise>
            </c:choose>
        </c:if>
                         
        <!-- Avviso di errore nella procedura del pagamento-->           
        <c:if test="${errore == true}" >
            <p class="error"> Si è verificato un errore nel pagamento, riprova! </p>
        </c:if>
     
                
        <label for="filtro">Filtra</label>
        <input type="text" name="filtro" id="filtro" value="" />

        <div id="content_bar">
            <table>
                <tr> 
                    <th> Nome </th> 
                    <th> Foto </th> 
                    <th> Quantit&aacute; </th>
                    <th> Prezzo </th>
                    <th> Acquista</th>
                </tr>

                <c:forEach var="articolo" items="${listaArticoli}" >
                    <tr> 
                        <td> ${articolo.nomeArticolo} </td> 
                        <td><img title="${articolo.nomeArticolo}" alt="${articolo.nomeArticolo}" src="${articolo.urlImmagine}" width="100" height="120" /></td> 
                        <td> ${articolo.quantita} </td>
                        <td> ${articolo.prezzo}</td>
                        <td><a href="cliente.html?idArticolo=${articolo.id}" class="cart">Aggiungi al carrello</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${size == 0}">
            <p class="error"> Al momento non ci sono articoli in vendita! Ci scusiamo! </p>
        </c:if>
               
        <jsp:include page="footer.jsp" />
    </body>
</html>
