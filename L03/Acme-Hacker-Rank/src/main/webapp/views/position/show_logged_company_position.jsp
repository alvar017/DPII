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

<jstl:if test="${position.status}">
<div class="content">
	<table>
		<tr><td><strong><spring:message code="position.title" />: </strong><jstl:out value="${position.title}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.description" />: </strong><jstl:out value="${position.description}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.salary" />: </strong><jstl:out value="${position.salary}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.status" />: </strong><spring:message code="position.status.${position.status}"/></td></tr>
		<tr><td><strong><spring:message code="position.deadline" />: </strong><jstl:out value="${position.deadline}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.skills" />: </strong><jstl:out value="${position.skills}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.techs" />: </strong><jstl:out value="${position.techs}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.ticker" />: </strong><jstl:out	value="${position.ticker}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.profile" />: </strong><jstl:out value="${position.profile}"></jstl:out></td></tr>
	</table>
	<jstl:if test="${position.cancel eq false }">
		<input type="button" value="<spring:message code='position.cancel_position' />" name="position.cancel_position" onclick="window.location = 'position/company/cancel.do?positionId=${position.id}'" />
	</jstl:if>
</div>
<input type="button" value="<spring:message code='position.cancel' />" name="position.cancel" onclick="window.location = 'position/company/list.do'" />
</jstl:if>
<!-- FORM IN CASE THIS IS NOT IN FINAL MODE -->
<jstl:if test="${not position.status}">
	<section id="main-content">
		<article>
			<div class="content">
				<form:form class="formularioEdicion" method="POST"
					modelAttribute="position" action="position/company/edit.do">
					<form:input path="id" type="hidden"/>
					
					<acme:textbox code="position.title" path="title" />
					<acme:textbox code="position.description" path="description" />
					<acme:textbox code="position.salary" path="salary"/>
					<acme:textbox code="position.deadline" path="deadline" placeholder="2021/12/25 15:30"/>
					<acme:textbox code="position.profile" path="profile" />
					<acme:textbox code="position.skills" path="skills" />
					<acme:textbox code="position.techs" path="techs" />
					
					<table style="width: 3em">
						<tr>
							<th><spring:message code="position.status"/></th>
							<td>
							<spring:message code="position.status.true"/>
							<form:radiobutton path="status" value="true"/> 
							</td>
							<td>
							<spring:message code="position.status.false"/>
							<form:radiobutton path="status" value="false" checked="checked"/> 
							</td>
							<td>
							<jstl:if test="${position.status eq false }">
								<input type="button" value="<spring:message code='position.delete' />" name="position.delete" onclick="window.location = 'position/company/delete.do?positionId=${position.id}'" />
							</jstl:if>
							</td>
						</tr>
					</table>
					<jstl:if test="${problemCountError}">
						<span class="error"><spring:message code="position.problemCountError"/> </span>
					</jstl:if>
					
					<acme:submit name="save" code="position.save"/>
					<input type="button" value="<spring:message code='position.cancel' />" onclick="window.location = 'position/company/list.do'" />
				</form:form>
			</div>
		</article>
	</section>
</jstl:if>