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

	<article>
		
<security:authorize access="hasRole('MEMBER')"> 
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="member" onsubmit="return phonenumberval()" action="member/edit.do">
				<form:hidden path="id" />
			
				<acme:textbox code="member.name" path="name" />
				<acme:textbox code="member.middleName" path="middleName" />
				<acme:textbox code="member.surname" path="surname" />
				<acme:textbox code="member.address" path="address" />
				<acme:phonebox code="member.phone" path="phone" />
				<acme:textbox code="member.email" path="email" />
				<acme:textbox code="member.photo" path="photo" />
				
				<acme:submit name="save" code="save"/>
				<acme:cancel url=" " code="cancel"/>
			</form:form>
		</div>
</security:authorize>
	</article>
</section>

