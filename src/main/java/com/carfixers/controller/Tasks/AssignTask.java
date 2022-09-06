/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carfixers.controller.Tasks;

import com.carfixers.dao.TaskDAO;
import com.carfixers.model.Task;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AssignTask", urlPatterns = {"/assign/*"})
public class AssignTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getPathInfo().split("/")[1]);
        Task task = TaskDAO.findById(id);
        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        String department = session.getAttribute("department").toString();
        int group_num = Integer.parseInt(session.getAttribute("group_num").toString());
        //----------------------- Security Check ----------------------//
        if (!role.equals("group_head") || !department.equals(task.getDepartment()) || group_num != task.getGroup_num()) {
            response.setStatus(403);
            request.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
        //----------------------- Security is OK, update task ---------------//
        if (TaskDAO.assignTask(task.getId(), request.getParameter("assignee"))) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }

}
