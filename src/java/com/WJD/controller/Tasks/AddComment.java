/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WJD.controller.Tasks;

import com.WJD.dao.CommentDAO;
import com.WJD.model.Comment;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CommentController", urlPatterns = {"/comments/*"})
public class AddComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String comment_desc = request.getParameter("comment_desc");
        String author = request.getSession().getAttribute("username").toString();
        int task_id = Integer.parseInt(request.getParameter("task_id"));
        Comment comment = new Comment(task_id, author, comment_desc);
        if (CommentDAO.insertComment(comment)) {
            response.sendRedirect(request.getContextPath() + "/tasks/" + task_id);
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
