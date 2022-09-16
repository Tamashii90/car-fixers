<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="departments" value="${requestScope.departments}"/>

<t:page>
    <jsp:attribute name="title">
        Add New Employee
    </jsp:attribute>
    <jsp:body>
        <%@include file="/WEB-INF/jspf/role.jspf" %>
                <script>
                    window.departments = ${departments};
                </script>
        <div id="react"></div>
        <script type="module" src="${pageContext.request.contextPath}/js/index.js"></script>
    </jsp:body>
</t:page>
