package com.carfixers.controller.Tasks;

import com.carfixers.dao.TaskDAO;
import com.carfixers.model.Task;
import java.io.IOException;
import java.time.LocalDate;
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

        String task_desc = request.getParameter("task_desc");
        String request_due_date = request.getParameter("due_date");
        String status = "available";
        int emp_id = (int) request.getSession().getAttribute("emp_id");
        LocalDate due_date;

        if (request_due_date.isEmpty()) {
            response.setStatus(400);
            request.setAttribute("msg", "Bad Request");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        due_date = LocalDate.parse(request_due_date);
        Task task = new Task(emp_id, task_desc, status, due_date);
        if (TaskDAO.insertTask(task)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.setStatus(500);
            request.setAttribute("msg", "Internal Server Error");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
