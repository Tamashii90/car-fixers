<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <jsp:attribute name="title">
        Dashboard
    </jsp:attribute>
    <jsp:body>
        <%@include file="/WEB-INF/jspf/role.jspf" %>  

        <div>
            <c:if test="${sessionScope.role.equals('ceo')}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/new/user">Add New Employee</a>
            </c:if>
            <c:if test="${sessionScope.role.equals('dep_head')}">
                <a href="${pageContext.request.contextPath}/new/task" class="btn btn-primary">Add New Task</a>
            </c:if>
            <c:choose>
                <c:when test="${requestScope.tasks.size() == 0}">
                    <h2 class="text-white">You have no tasks..</h2>
                </c:when>
                <c:otherwise>
                    <table class="mt-3">
                        <thead>
                            <tr>
                                <th>Assignee</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Department</th>
                                <th>Group</th>
                                <th>Added On</th>
                                <th>Due By</th>
                                <th>Details</th>
                            </tr> 
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.tasks}" var="task">
                                <tr>
                                    <td>${task.status == "available" ? "NONE" : task.assignee}</td>
                                    <td>
                                        ${task.task_desc}
                                    </td>
                                    <td>${task.status}</td>
                                    <td>${task.dep_name}</td>
                                    <td>${task.group_name}</td>
                                    <td>${task.date_added_on}</td>
                                    <td>${task.due_date}</td>
                                    <td><a href="${pageContext.request.getContextPath()}/tasks/${task.id}">View</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</t:page>
