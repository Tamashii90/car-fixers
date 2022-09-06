<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="task" value="${requestScope.task}"/>

<t:page>
    <jsp:attribute name="title">
        Task
    </jsp:attribute>
    <jsp:body>
        <%@include file="/WEB-INF/jspf/role.jspf" %>
        <form class="mb-4" id="task-form">
            <div class="form-group row">
                <label class="col-2 col-form-label">Assignee</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.assignee == null ? "-" : task.assignee}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">Description</label>
                <div class="col-7">
                    <textarea class="form-control-plaintext" readonly>${task.task_desc}</textarea>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">Status</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.status}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">Department</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.department}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">Group</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.group_num}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">Added On</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.date_added_on}"/>
                </div>
            </div>
            <div class="form-group row pb-3">
                <label class="col-2 col-form-label">Due By</label>
                <div class="col-7">
                    <input class="form-control-plaintext" type="text" readonly value="${task.due_date}"/>
                </div>
            </div>
        </form>
        <h3 class="my-4">Change Status</h3>
        <form method="POST" action="${pageContext.request.contextPath}/mark/${task.id}">
            <div class="form-group row">
                <label class="col-2 col-form-label">Mark As</label>
                <div class="col-7">
                    <select class="custom-select" name="status">
                        <option value="complete">Complete</option>
                        <option value="has_issues">Has Issues</option>
                        <c:if test="${!sessionScope.role.equals('group_member')}">
                            <option value="confirmed_complete">Confirmed Complete</option>
                            <option value="cancelled">Cancelled</option>
                        </c:if>
                    </select>
                </div>
            </div>
            <div class="text-right col-9">
                <button type="submit" class="btn btn-primary" style="position: relative;left: 20px;">Mark</button>
            </div>
        </form>
        <c:if test="${sessionScope.role.equals('group_head') && task.assignee == null}">
            <h3 class="my-4">Assign task</h3>
            <form class="mb-4" method="POST" action="${pageContext.request.contextPath}/assign/${task.id}">
                <div class="form-group row">
                    <label class="col-2 col-form-label">Assign to</label>
                    <div class="col-7">
                        <select class="form-control-plaintext" name="assignee" >
                            <c:forEach items="${requestScope.users}" var="user">
                                <option value="${user}">${user}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="text-right col-9">
                    <button type="submit" class="btn btn-primary" style="position: relative;left: 20px;">Assign</button>
                </div>
            </form>
        </c:if>
        <%@include file="/WEB-INF/jspf/comment.jspf" %>
    </jsp:body>
</t:page>


