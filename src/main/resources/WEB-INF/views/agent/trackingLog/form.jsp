<%--
- form.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="agent.trackingLog.form.label.step" path="step" />
	<acme:input-moment code="agent.trackingLog.form.label.lastUpdateMoment" path="lastUpdateMoment" readonly="true"/>
	<acme:input-double code="agent.trackingLog.form.label.resolutionPercentage" path="resolutionPercentage"/>
	<acme:input-select code="agent.trackingLog.form.label.status" path="status" choices="${status}" />
	<acme:input-textarea code="agent.trackingLog.form.label.resolution" path="resolution"/>
	<jstl:choose>
	<jstl:when test="${_command == 'create'}">
		<acme:submit  code="agent.claim.form.button.create" action="/agent/claim/create?masterId=${masterId}"/>
	</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode==true }">
		<acme:submit  code="agent.trackingLog.form.button.update" action="/agent/trackingLog/update?masterId=${masterId}"/>
		<acme:submit  code="agent.trackingLog.form.button.publish" action="/agent/trackingLog/publish"/>
		<acme:submit  code="agent.trackingLog.form.button.delete" action="/agent/trackingLog/delete"/>
	</jstl:when>	
	</jstl:choose>
</acme:form>
