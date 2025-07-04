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

	 <acme:input-textbox code="manager.flight.form.label.tag" path="tag"/>
	<acme:input-textbox code="manager.flight.form.label.selfTransfer" path="selfTransfer"/>
	<acme:input-money code="manager.flight.form.label.cost" path="cost"/>
	<acme:input-textbox code="manager.flight.form.label.description" path="description"/>
	<acme:input-textbox code="manager.flight.form.label.draftMode" path="draftMode"/>
	
	<jstl:choose>
	<jstl:when test="${_command == 'create'}">
		<acme:submit  code="manager.flight.form.button.create" action="/technician/task/create?masterId=${masterId}"/>
	</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode==true }">
		<acme:submit  code="manager.flight.form.button.update" action="/technician/task/update?masterId=${masterId}"/>
		<acme:submit  code="manager.flight.form.button.publish" action="/technician/task/publish"/>
		<acme:submit  code="manager.flight.form.button.delete" action="/technician/task/delete"/>
	</jstl:when>	
	</jstl:choose>
</acme:form>	