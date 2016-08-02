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
        <title>Riepilogo</title>
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


            <header>
                <h1 class="titolo1">Come Nuovo</h1>
                <nav>
                    <ul>
                        <li id="login"><a href="login.html">Login</a></li>
                        <li><a href="descrizione.html">Descrizione</a></li>
                        <c:choose>
                            <c:when test="${utente == 'venditore'}" >
                              <li> <a href="venditore.html">Venditore</a> </li>
                            </c:when>
                            <c:otherwise>
                              <li> <a href="cliente.html">Cliente</a> </li>
                            </c:otherwise>
                        </c:choose> 
                        <li>
                            <!-- Se è stato effettuato il login da un utente allora comparirà il link di logout-->
                            <c:if test="${sessionScope.utente != null}">
                                <div id="logout"><a href="logout.html">Logout</a></div>
                            </c:if>
                        </li>
                    </ul>
                </nav>
            </header>
            
            <jsp:include page="sidebar.jsp" />

            <div id="content_bar">
                
                <c:choose>
                    <c:when test="${utente == 'venditore'}" >
                        <c:if test="${conferma != null}"> 
                            <h1>Riepilogo del nuovo articolo</h1>
                        </c:if>
                        <c:if test="${modifica != null}"> 
                            <h1>Riepilogo dell'articolo modificato</h1>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                         <h1>Riepilogo dell'articolo che stai per acquistare</h1>
                    </c:otherwise>
                </c:choose>
                
                <table>
                    <tr> 
                        <th> Nome </th> 
                        <th> Foto </th> 
                        <th> Quantit&aacute; </th>
                        <th> Prezzo </th>
                    </tr>
                        <tr> 
                            <td> ${articolo.nomeArticolo} </td> 
                            <td> 
                                <c:if test="${ articolo.urlImmagine != null}">   
                                  <img title="${articolo.nomeArticolo}" alt="${articolo.nomeArticolo}" src="${articolo.urlImmagine}" width="100" height="120" />
                                </c:if>
                            </td> 
                            <td> 
                                <!-- Se il riepilogo riguarda il cliente, la quantità sarà pari a uno, altrimenti a quanto inserito dal venditore-->
                                <c:choose>
                                    <c:when test="${utente == 'venditore'}" >
                                        ${articolo.quantita} 
                                    </c:when>
                                    <c:otherwise>
                                        1
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td> ${articolo.prezzo} </td>
                        </tr>
                </table>
                <div id="descr_oggetto">
                    <h3>Descrizione dell'articolo</h3>       
                    <p>${articolo.descrizione}</p>       
                </div>
                
                <!-- Se l'utente è un venditore lo informo che l'azione eseguita sia avvenuta con successo -->
                <c:if test="${utente == 'venditore'}">
                        <c:if test="${conferma != null}">        
                             <c:if test="${conferma == true}">     
                                     <p class="ok"> Inserimento avvenuto con successo! </p>
                             </c:if>
                        </c:if>
                               
                        <c:if test="${modifica != null}">        
                            <c:if test="${modifica != null}">        
                               <c:if test="${modifica == true}">     
                                     <p class="ok"> Modifica avvenuta con successo! </p>
                               </c:if>
                            </c:if>
                        </c:if>
                </c:if>
              
                <!-- Se il riepilogo è relativo al cliente mostro il pulsante per confermare l'acquisto -->
                <c:if test="${utente == 'cliente'}">
                    <p> Clicca qui sotto per confermare l'acquisto<p>
                    <!-- l'id dell'articolo viene passato all'url per trattarla come variabile di pagina -->
                    <form action="cliente.html?idSelezionato=${articolo.id}" method="post">
                        <button type="submit" value="Conferma" id="conferma" name="conferma">Conferma</button>
                    </form>
                </c:if>
                
            </div>
  
            <jsp:include page="footer.jsp" />
    </body>
</html>
 