<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <jsp:attribute name="title">
        Add New Task
    </jsp:attribute>
    <jsp:body>
        <%@include file="/WEB-INF/jspf/role.jspf" %>  
        <form class="mb-4" method="POST">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Department</label>
                <div class="col-7">
                    <input class="form-control" name="department" type="text" disabled value="${sessionScope.department}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Description</label>
                <div class="col-7">
                    <textarea class="form-control" name="task_desc" placeholder="Task Description.." required></textarea>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Status</label>
                <div class="col-7">
                    <select class="custom-select" name="status" disabled title="Only group heads assign these">
                        <option value="available" selected>available</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Group</label>
                <div class="col-7">
                    <select class="custom-select" name="group_num">
                            <option>1</option>
                            <option>2</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Due By</label>
                <div class="col-7">
                    <input class="form-control" type="date" name="due_date" required>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Add Task</button>

        </form>
    </jsp:body>
</t:page>
