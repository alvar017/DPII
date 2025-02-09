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

<div class="content">
	<table>
		<c:choose>
    		<c:when test="${company.photo==''}">
				<tr><td><spring:message code="company.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="company.photo" /><br>
				<img width="95" src="${company.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><strong><spring:message code="company.name" /></strong> <jstl:out	value="${company.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="company.surname" /></strong> <jstl:out value="${company.surname}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="company.address" /></strong> <jstl:out value="${company.address}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="company.email" /></strong> <jstl:out value="${company.email}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="company.phone" /></strong> <jstl:out value="${company.phone}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="company.comercialName" /></strong> <jstl:out value="${company.commercialName}"></jstl:out></td></tr>

	</table>
</div>
<input type="button" value="back" name="company.cancel" onclick="history.back()" />
<jstl:if test="${checkCompany}">
<acme:cancel url="/company/export.do?id=${company.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/company/delete.do?id=${company.id}" code="delete"/>
</jstl:if>
