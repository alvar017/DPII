<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<p><spring:message code="editMessage" /></p>
		
		<div class="container-fluid"  style="padding-left: 2.5em" >
			<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="msg" action="message/editMailbox.do">
				<form:hidden path="id" />
				<fieldset>
						<form:label path="mailboxes">
			<spring:message code="msg.mailboxes" />:
		</form:label>
		<form:select path="mailboxes">
   		 	<form:options items="${nameMailbox}" />
		</form:select>
		<form:errors cssClass="error" path="mailboxes" />
				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-8">
						<acme:submit name="save" code="actor.save" />
						<acme:cancel url="javascript:relativeRedir('mailbox/list.do');" code="msg.cancel"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	