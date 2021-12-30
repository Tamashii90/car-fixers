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
            <h3 class="text-white mb-4 pb-2">Log In To Your Account</h3>
            <form style="margin:0 !important;" action="${pageContext.request.contextPath}/login" method="POST" >
                <div class="form-group">
                    <label>Username</label>
                    <input class="form-control" type="text" name="username" minlength="3" placeholder="Username.." />
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input class="form-control" type="password" name="password" minlength="7" placeholder="Password.."/>
                </div>
                <button type="submit" class="btn btn-primary">Log in</button>
            </form>
        </div>
                <div class="text-white p-3" style="background: #1e1b1b63;border: 2px solid #ed4077;">
                    <h4>For testing purposes:</h4>
                    <ul>
                        <li>CEO: admin</li>
                        <li>Department Head: mercury</li>
                        <li>Group Head: venus</li>
                        <li>Group Member: saturn</li>
                    </ul>
                    <span>And all have the same password: 1234567</span>
                </div>
    </jsp:body>
</t:page>
