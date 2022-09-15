package com.carfixers.controller.Users;

import com.carfixers.dao.UserDAO;
import com.carfixers.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        try {
            user = UserDAO.findEmpByNickname(username);
        } catch (Exception ex) {
            response.setStatus(500);
            response.sendRedirect(request.getContextPath() + "/?msg=" + ex.getMessage());
            return;
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/?msg=Invalid Credentials");
            return;
        }
        boolean match = BCrypt.checkpw(password, user.getPassword());
        if (match) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("emp_id", user.getEmp_id());
            session.setAttribute("role", user.getRole());
            session.setAttribute("group_id", user.getGroup_id());
            session.setAttribute("dep_id", user.getDep_id());
            session.setAttribute("group_name", user.getGroup_name());
            session.setAttribute("dep_name", user.getDep_name());
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/?msg=Invalid Credentials");
        }

    }
}
