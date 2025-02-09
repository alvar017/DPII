<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<section id="main-content">
<security:authorize access="hasRole('ADMIN')"> 

	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="registrationForm" onsubmit="return phonenumberval();" action="administrator/create.do">

				
				<acme:textbox code="admin.name" path="name" />
				<acme:textbox code="admin.middleName" path="middleName" />
				<acme:textbox code="admin.surname" path="surname" />
				<acme:textbox code="admin.username" path="userName" />
				<acme:password code="admin.password" path="password" />
				<acme:password code="admin.passwordC" path="confirmPassword" />
				<acme:textbox code="admin.address" path="address" />
				<acme:textbox code="admin.email" path="email" />
				<acme:textbox code="admin.photo" path="photo" />
				<acme:phonebox code="admin.phone" path="phone"/>
				
				<br>

				<spring:message code="conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="brotherhood/conditions.do" target="_blank"><spring:message code="conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				
				<br>
				<br>	
				<acme:submit name="save" code="save"/>
				<acme:cancel url=" " code="cancel"/>
			</form:form>
		</div>
	</article>
</security:authorize>
</section>

