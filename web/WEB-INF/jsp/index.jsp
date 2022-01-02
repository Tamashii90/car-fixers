<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% if (session.getAttribute("username") != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
%>

<t:page>
    <jsp:attribute name="title">
        Car Fixers
    </jsp:attribute>
    <jsp:body>
        <div id="login-container">
            <h3 class="text-white mb-4 pb-2">Log in to Your Account</h3>
            <form style="margin:0 !important;" action="${pageContext.request.contextPath}/login" method="POST" >
                <div class="form-group">
                    <label>Username</label>
                    <select class="custom-select" name="username">
                        <option value="admin">admin (CEO)</option>
                        <option value="yousef">Yousef (Department Head)</option>
                        <option value="sarah">Sarah (Group Head)</option>
                        <option value="shinji">Shinji (Group Member)</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input class="form-control" type="password" name="password" minlength="7" placeholder="Password.." value="1234567"/>
                </div>
                <button type="submit" class="btn btn-primary">Log in</button>
            </form>
        </div>
    </jsp:body>
</t:page>
