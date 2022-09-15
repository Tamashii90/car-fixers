/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carfixers.controller.Tasks;

import com.carfixers.dao.GroupDAO;
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

        int task_id = Integer.parseInt(request.getPathInfo().split("/")[1]);
        Task task = TaskDAO.findById(task_id);
        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        int group_id = Integer.parseInt(session.getAttribute("group_id").toString());
        //----------------------- Security Check ----------------------//

        // Only group HEAD from the SAME group can assign tasks
        if (!role.equals("group_head") || group_id != GroupDAO.findGroupIdByTaskId(task_id)) {
            response.setStatus(403);
            request.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

        //----------------------- Security is OK, assign task ---------------//
        if (TaskDAO.assignTask(task.getId(), request.getParameter("assignee"))) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }

}
