/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WJD.controller.Tasks;

import com.WJD.dao.TaskDAO;
import com.WJD.dao.UserDAO;
import com.WJD.model.Task;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddTask", urlPatterns = {"/new/task"})
public class AddTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        if (!role.equals("dep_head")) {
            response.setStatus(403);
            request.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/addTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String department = request.getSession().getAttribute("department").toString();
        int group_num = Integer.parseInt(request.getParameter("group_num"));
        String task_desc = request.getParameter("task_desc");
        String request_due_date = request.getParameter("due_date");
        String status = "available";
        String assignee = "none";
        LocalDate due_date = null;

        if (request_due_date.isEmpty()) {
            response.setStatus(400);
            request.setAttribute("msg", "Bad Request");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        due_date = LocalDate.parse(request_due_date);
        Task task = new Task(assignee, task_desc, status, due_date, department, group_num);
        if (TaskDAO.insertTask(task)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
