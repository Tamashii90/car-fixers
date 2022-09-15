/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carfixers.controller.Tasks;

import com.carfixers.dao.*;
import com.carfixers.model.Comment;
import com.carfixers.model.Task;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "TaskController", urlPatterns = {"/tasks/*"})
public class ViewTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Task task = null;
        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        int emp_id = (int) session.getAttribute("emp_id");
        int group_id = (int) session.getAttribute("group_id");
        int dep_id = (int) session.getAttribute("dep_id");
        int task_id = -123;
        try {
            // check if task exists (maybe user manually entered a url)
            task_id = Integer.parseInt(request.getPathInfo().split("/")[1]);
            task = TaskDAO.findById(task_id);
        } catch (Exception e) {
            response.setStatus(404);
            request.setAttribute("msg", "Not Found");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

        // If not the ceo, then check if user is allowed to view the task
        if (!role.equals("ceo")) {

            boolean isSecured = true;
            switch (role) {
                case "group_member":
                    if (task.getEmp_id() != emp_id) {
                        isSecured = false;
                    }
                    break;
                case "group_head":
                    if (GroupDAO.findGroupIdByTaskId(task_id) != group_id) {
                        isSecured = false;
                    }
                    break;
                case "dep_head":
                    if (DepartmentDAO.findDepIdByGroupId(group_id) != dep_id) {
                        isSecured = false;
                    }
            }
            if (!isSecured) {
                response.setStatus(403);
                request.setAttribute("msg", "Unauthorized");
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        }
        //---------- Security is OK, fetch comments then return the task  --------------//
        List<Comment> comments = CommentDAO.findTaskComments(task.getId());
        if (role.equals("group_head") && task.getStatus().equals("available")) {
            // Get list of users in a group for group_head to assign the task to
            List<String> availableUsers = UserDAO.findAssignableUsers(task.getId());
            request.setAttribute("users", availableUsers);
        }
        request.setAttribute("task", task);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/jsp/task.jsp").forward(request, response);
    }
}
