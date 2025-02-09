<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<body>

	<div>
    	<form:form class="formularioEdicion" method="POST" onsubmit="return phonenumberval();" modelAttribute="curricula" action="curricula/hacker/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	<acme:textbox path="name" code="curricula.name"/>
          	<acme:textbox path="statement" code="curricula.statement"/>
			<acme:phonebox code="curricula.phone" path="phone" />
          	<acme:textbox path="linkGitHub" code="curricula.nameGitHub"/>
          	<acme:textbox path="linkLinkedin" code="curricula.linkLinkedin"/>
          	<fieldset>
          		<legend>
          			<i><spring:message code="curricula.optionalMiscellaneous" /></i><img width="35" height="35" src="./images/att.png" alt="${row1.id}" />
          		</legend>
          		<acme:textbox path="miscellaneous" code="curricula.miscellaneous"/>
          	</fieldset>
          	<acme:submit name="save" code="save"/>
          	<c:choose>
          		<c:when test="${curricula.id == 0}">
          			<acme:cancel url="curricula/list.do?" code="back"/>
          		</c:when>
          		<c:otherwise>
          			<acme:cancel url="curricula/show.do?curriculaId=${curricula.id}" code="back"/>
          		</c:otherwise>
          	</c:choose>
		</form:form>
	</div>
</body>
