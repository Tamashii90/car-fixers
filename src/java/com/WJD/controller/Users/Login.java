/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WJD.controller.Users;

import com.WJD.dao.UserDAO;
import com.WJD.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        try {
            user = UserDAO.findUser(username);
        } catch (Exception ex) {
            response.setStatus(500);
            response.sendRedirect(request.getContextPath() + "/?msg=" + ex.getMessage());
            return;
        }
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/?msg=Invalid Credentials");
            return;
        }
        boolean match = BCrypt.checkpw(password, user.getPassword());
        if (match) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("group_num", user.getGroup_num());
            session.setAttribute("department", user.getDepartment());
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/?msg=Invalid Credentials");
        }

    }
}
