<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <jsp:attribute name="title">
        Add New Employee
    </jsp:attribute>
    <jsp:body>
        <%@include file="/WEB-INF/jspf/role.jspf" %>  
        <form method="POST">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">First Name</label>
                <div class="col-7">
                    <input class="form-control" type="text" maxlength="20" name="first_name" value="firstName"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Last Name</label>
                <div class="col-7">
                    <input class="form-control" type="text" maxlength="20" name="last_name" value="lastName"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Username</label>
                <div class="col-7">
                    <input class="form-control" type="text" name="username" required minlength="3" maxlength="13"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password</label>
                <div class="col-7">
                    <input class="form-control" type="password" name="password" minlength="7" required/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Department</label>
                <div class="col-7">
                    <select class="custom-select" name="department" required>
                        <option value="repair" selected>Repair</option>
                        <option value="accounting">Accounting</option>
                        <option value="customer_support">Customer Support</option>
                        <option value="quality_assurance">Quality Assurance</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Group</label>
                <div class="col-7">
                    <select class="custom-select" name="group_num" required>
                        <option value="1" selected>1</option>
                        <option value="2">2</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Role</label>
                <div class="col-7">
                    <select class="custom-select" name="role" required>
                        <option value="group_member">Group Member</option>
                        <option value="group_head">Group Head</option>
                        <option value="dep_head">Department Head</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" style="position: relative;left: -15px;">Add Employee</button>
        </form>
    </jsp:body>
</t:page>
