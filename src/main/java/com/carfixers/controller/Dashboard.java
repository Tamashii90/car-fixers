package com.carfixers.controller;

import com.carfixers.dao.TaskDAO;
import com.carfixers.model.Task;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Dashboard", urlPatterns = {"/dashboard"})
public class Dashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        String role = session.getAttribute("role").toString();
        String department = session.getAttribute("dep_name").toString();
        int group_id = (int) session.getAttribute("group_id");
        List<Task> tasks = null;

        switch (role) {
            case "group_member":
                tasks = TaskDAO.findAllowedTasks(username, group_id);
                break;
            case "group_head":
                tasks = TaskDAO.findGroupTasks(group_id);
                break;
            case "dep_head":
                tasks = TaskDAO.findDepartTasks(department);
                break;
            case "ceo":
                tasks = TaskDAO.findAllTasks("due_date", "asc");
                break;
        }
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }
}
