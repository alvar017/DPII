<%--
 * textbox.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!-- script  -->
		<header>
			<script type="text/javascript">
				function phonenumberval() {
					var phoneNumber;
					phoneNumber = document.getElementById("phoneNumber").value;
					var res = false;
					if (/(\+[0-9]{1,3})[ ](\([0-9]{1,3}\))[ ]([0-9]{4,})$/.test(phoneNumber)) {
						res = true;
					}
					if (/(\+[0-9]{1,3})\s([0-9]{4,})$/.test(phoneNumber)) {
				res = true;
					}
					if (/^([0-9]{4,})\:(\+[0-9]{1,3})$/.test(phoneNumber)) {
						res = true;
					}
					if (/^([0-9]{4,})$/.test(phoneNumber)) {
						res = true;
						alert("<spring:message code='PN' />");
					}
				}
			</script>
		</header>
<!-- script  -->


<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="readonly" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<%-- Definition --%>
<div>
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>	
	<form:input path="${path}" id="phoneNumber" />	
	<form:errors path="${path}" cssClass="error" />
</div>