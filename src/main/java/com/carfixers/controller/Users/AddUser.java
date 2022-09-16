package com.carfixers.controller.Users;

import com.carfixers.dao.DepartmentDAO;
import com.carfixers.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.carfixers.dao.UserDAO;

import javax.servlet.http.HttpSession;

@WebServlet(name = "get", urlPatterns = {"/new/user"})
public class AddUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        Map<String, List<String>> map = DepartmentDAO.findAllDepsAndGroups();

        StringBuilder json = new StringBuilder("{");
        for (String department : map.keySet()) {
            json.append(department).append(": [");
            for (String group: map.get(department)) {
                json.append("\"").append(group).append("\"").append(",");
            }
            json = new StringBuilder(json.substring(0, json.length() - 1));
            json.append("], ");
        }
        json.append("}");

        if (!role.equals("ceo")) {
            response.setStatus(403);
            request.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
        request.setAttribute("departments", json.toString());
        request.getRequestDispatcher("/WEB-INF/jsp/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String requesterRole = session.getAttribute("role").toString();
        if (!requesterRole.equals("ceo")) {
            response.setStatus(403);
            request.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String group_name = request.getParameter("group_name");
        String role = "group_member";
        User user = new User(username, first_name, last_name, password, role, group_name);
        if (UserDAO.insertUser(user)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }

}