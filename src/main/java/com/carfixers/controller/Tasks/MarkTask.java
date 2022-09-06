/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carfixers.controller.Tasks;

import com.carfixers.dao.TaskDAO;
import com.carfixers.model.Task;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MarkTask", urlPatterns = {"/mark/*"})
public class MarkTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getPathInfo().split("/")[1]);
        String status = request.getParameter("status");
        if (TaskDAO.markTask(id, status)) {
            response.sendRedirect(request.getContextPath() + "/tasks/" + id);
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
