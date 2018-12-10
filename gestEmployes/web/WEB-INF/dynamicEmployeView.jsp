<%-- 
    Document   : errorMessage
    Created on : 7 déc. 2018, 20:23:34
    Author     : Clément
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 

<!-- Display action type -->    
<c:choose>
    <c:when test="${requestScope.actionChoosed ==0}">
        <h1 class="page-header">Ajouter un employé</h1>
    </c:when>
    <c:otherwise>
         <h1 class="page-header">Détails du membre 
             <c:out value="${sessionScope.employe.prenom}" default=""></c:out>
             <c:out value="${sessionScope.employe.nom}" default=""></c:out>
         </h1>
    </c:otherwise>
</c:choose>
        
<!-- Display error Message -->       
<c:if test="${requestScope.errorMessageEmploye !=''}">
    <p style="color:firebrick;">
        <c:out value="${requestScope.errorMessageEmploye}" default=""></c:out>
    </p>
    <c:remove var="errorMessageEmploye" scope="session"></c:remove>
</c:if>
