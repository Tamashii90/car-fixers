/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WJD.controller.Tasks;

import com.WJD.model.Comment;
import com.WJD.dao.TaskDAO;
import com.WJD.dao.CommentDAO;
import com.WJD.dao.UserDAO;
import com.WJD.model.Task;
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
        try {
            // check if task exists (maybe user manually entered a url)
            int id = Integer.parseInt(request.getPathInfo().split("/")[1]);
            task = TaskDAO.findById(id);
        } catch (Exception e) {
            response.setStatus(404);
            request.setAttribute("msg", "Not Found");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

        // If not the ceo, then check if user is allowed to view the task
        if (!role.equals("ceo")) {
            String username = session.getAttribute("username").toString();
            int group_num = (int) session.getAttribute("group_num");
            String department = session.getAttribute("department").toString();
            
            //---------- Security Check --------------//
            switch (role) {
                case "group_member":
                    if (task.getAssignee() != null && !task.getAssignee().equals(username)) {
                        response.setStatus(403);
                        request.setAttribute("msg", "Unauthorized");
                        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                    }
                    break;
                case "group_head":
                    if (task.getGroup_num() != group_num) {
                        response.setStatus(403);
                        request.setAttribute("msg", "Unauthorized");
                        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                    }
                    break;
                case "dep_head":
                    if (!task.getDepartment().equals(department)) {
                        response.setStatus(403);
                        request.setAttribute("msg", "Unauthorized");
                        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                    }
                    break;
            }
        }
        //---------- Security is OK, fetch comments then return the task  --------------//
        List<Comment> comments = CommentDAO.findTaskComments(task.getId());
        if (role.equals("group_head") && task.getAssignee() == null) {
            // Get list of users in a group for group_head to assign the task to
            List<String> availableUsers = UserDAO.findAssignableUsers(task.getDepartment(), task.getGroup_num());
            request.setAttribute("users", availableUsers);
        }
        request.setAttribute("task", task);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/jsp/task.jsp").forward(request, response);
    }
}
