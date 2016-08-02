<%-- 
    Document   : articoliJson
    Created on : 20-lug-2016, 18.38.12
    Author     : utente
--%>

<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:array>
    <c:forEach var="articolo" items="${listaArticoli}">
        <json:object>
            <json:property name="nomeArticolo" value="${articolo.nomeArticolo}"/>
            <json:property name="urlImmagine" value="${articolo.urlImmagine}"/>
            <json:property name="descrizione" value="${articolo.descrizione}"/>
            <json:property name="prezzo" value="${articolo.prezzo}"/>
            <json:property name="quantita" value="${articolo.quantita}"/>
            <json:property name="id" value="${articolo.id}"/>
        </json:object>
    </c:forEach>
</json:array>


