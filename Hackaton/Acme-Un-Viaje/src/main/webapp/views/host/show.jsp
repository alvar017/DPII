<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">

		<c:choose>
			<c:when
				test="${registerActor.photo == null or registerActor.photo=='' }">
				<div class="col-md-3">
					<div class="card">
						<img class="card-img-top" src="images/registerPhoto.png"
							alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${registerActor.name}
								${registerActor.surname}</h4>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-3">
					<div class="card">
						<img class="card-img-top" src="${registerActor.photo}" alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${registerActor.name}
								${registerActor.surname}</h4>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<br>

		<div class="col-md-4">
			<fieldset>
				<h2>
					<spring:message code="actor.personalData" />
					&nbsp;<i class="fas fa-mobile-alt icon-gradient"></i>
				</h2>
				<hr>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="actor.name" />:&nbsp;</strong>
					<jstl:out value="${registerActor.name}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="actor.surname" />:&nbsp;</strong>
					<jstl:out value="${registerActor.surname}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="actor.email" />:&nbsp;</strong>
					<jstl:out value="${registerActor.email}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="actor.phone" />:&nbsp;</strong>
					<jstl:out value="${registerActor.phone}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<acme:displayDate date="${registerActor.birthDate}"
						code="actor.birthDate" />
				</div>
			</fieldset>
		</div>

		<c:choose>
			<c:when test="${res}">
				<div class="col-md-4">
					<fieldset>
						<h2>
							<spring:message code="actor.creditCard" />
							&nbsp;<i class="far fa-credit-card icon-gradient"></i>
						</h2>
						<hr>
						<div class="row" style="padding-left: 1.5em">
							<strong><spring:message code="actor.holder" />:&nbsp;</strong>
							<jstl:out value="${registerActor.creditCard.holder}"></jstl:out>
						</div>

						<div class="row" style="padding-left: 1.5em">
							<strong><spring:message code="actor.make" />:&nbsp;</strong>
							<jstl:out value="${registerActor.creditCard.make}"></jstl:out>
						</div>
						<div class="row" style="padding-left: 1.5em">
							<strong><spring:message code="actor.number" />:&nbsp;</strong>
							<jstl:out value="${registerActor.creditCard.number}"></jstl:out>
						</div>

						<div class="row" style="padding-left: 1.5em">
							<strong><spring:message code="actor.CVV" />:&nbsp;</strong>
							<jstl:out value="${registerActor.creditCard.CVV}"></jstl:out>
						</div>
						<div class="row" style="padding-left: 1.5em">
							<strong><spring:message code="actor.expiration" />:&nbsp;</strong>
							<jstl:out value="${registerActor.creditCard.expiration}"></jstl:out>
						</div>
					</fieldset>
				</div>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- ALVARO -->
	<br>
	<jstl:if test="${validCleaner}">	
		<div class="jumbotron" style="width: 100%">
			<div class="row">
				<div class="col-md-12">
					<h4 class="display-12" style="text-align: center">
						<spring:message code="workforce" />
						<a href="jobApplication/cleaner/create.do?hostId=${registerActor.id}">
							<spring:message htmlEscape="false" code="yourApplication"/>
						</a>	
					</h4>
				</div>
			</div>
		</div>
	</jstl:if>
	<!-- ALVARO -->
	<c:choose>
		<c:when test="${validCleaner}">
			<div class="row">
				<div class="col-md-4">
					<acme:historyBack/>
				</div>
			</div>		
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-md-4">
			<acme:cancel url="" code="actor.back" />
			<jstl:if test="${owner}">
			<acme:cancel url="/host/export.do?id=${registerActor.id}" code="export"/><br>
			<br>
			<spring:message code="delete.actor"></spring:message><br>
			<br>
			<acme:cancel url="/host/delete.do?id=${registerActor.id}" code="delete"/><br>
			<spring:message code="delete.host"></spring:message>
			</jstl:if>
		</div>

			</div>
		</c:otherwise>
	</c:choose>
</div>
