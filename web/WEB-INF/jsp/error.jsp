<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="code" value=" ${pageContext.response.getStatus()}" />
<c:set var="msg" value=" ${requestScope.msg}" />

<t:page>
    <jsp:attribute name="title">
        ${msg}
    </jsp:attribute>
    <jsp:body>
        <h2 class="text-white" style="text-align: center;margin-top: 50px;">${code} | ${msg}</h2>
    </jsp:body>
</t:page>

