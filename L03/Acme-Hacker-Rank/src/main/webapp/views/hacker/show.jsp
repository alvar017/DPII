<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('HACKER')"> 
<div class="content">
	<form method="get" action="curricula/list.do">
		<button class="linea" name="hackerId" value="${hacker.id}"><spring:message code="show.curricula"/></button>
	</form>
	<table>
		<c:choose>
    		<c:when test="${hacker.photo==''}">
				<tr><td><spring:message code="hacker.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="hacker.photo" /><br>
				<img width="95" src="${hacker.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><strong><spring:message code="hacker.name" /></strong> <jstl:out	value="${hacker.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="hacker.surname" /></strong> <jstl:out value="${hacker.surname}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="hacker.address" /></strong> <jstl:out value="${hacker.address}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="hacker.email" /></strong> <jstl:out value="${hacker.email}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="hacker.phone" /></strong> <jstl:out value="${hacker.phone}"></jstl:out></td></tr>
	</table>
</div>
</security:authorize>
<acme:cancel url=" " code="hacker.cancel"/>
<acme:cancel url="/hacker/export.do?id=${hacker.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/hacker/delete.do?id=${hacker.id}" code="delete"/>