package com.WJD.dao;

import com.WJD.model.Task;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TaskDAO {

    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_TASK_STMT = "SELECT * FROM tasks WHERE tasks.id = ?;";
    private static final String FIND_ALLOWED_TASKS_STMT = "SELECT * FROM tasks WHERE department=? AND group_num=? AND (tasks.assignee = ? OR tasks.assignee IS NULL);";
    private static final String FIND_DEPART_TASKS_STMT = "SELECT tasks.* FROM tasks WHERE tasks.department = ?";
    private static final String FIND_ALL_TASKS_STMT = "SELECT * FROM tasks";
    private static final String FIND_GROUP_TASKS_STMT = "SELECT * FROM tasks WHERE tasks.department = ? AND tasks.group_num = ?;";

    private static final String INSERT_TASK_STMT = "INSERT INTO tasks (assignee, department, group_num, task_desc, status, due_date, date_added_on) VALUES (?,?,?,?,?,?,?);";

    private static final String ASSIGN_TASK_STMT = "UPDATE tasks SET assignee = ?, status='assigned' WHERE tasks.id = ?;";
    private static final String MARK_TASK_STMT = "UPDATE tasks SET status = ? WHERE tasks.id = ?;";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static List<Task> findAllowedTasks(String department, int group_num, String assignee) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALLOWED_TASKS_STMT);) {
            preparedStatement.setString(1, department);
            preparedStatement.setInt(2, group_num);
            preparedStatement.setString(3, assignee);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                // because assignee might be null, don't use assignee from above
                String realAssignee = rs.getString("assignee");
                String task_desc = rs.getString("task_desc");
                String status = rs.getString("status");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                LocalDate due_date = LocalDate.parse(rs.getString("due_date"));
                Task task = new Task(id, realAssignee, task_desc, status, date_added_on, due_date, group_num, department);
                tasks.add(task);
            }

        } catch (SQLException e) {
        }
        return tasks;
    }

    public static List<Task> findGroupTasks(String department, int group_num) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_GROUP_TASKS_STMT);) {
            preparedStatement.setString(1, department);
            preparedStatement.setInt(2, group_num);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String task_desc = rs.getString("task_desc");
                String status = rs.getString("status");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                LocalDate due_date = LocalDate.parse(rs.getString("due_date"));
                String assignee = rs.getString("assignee");
                Task task = new Task(id, assignee, task_desc, status, date_added_on, due_date, group_num, department);
                tasks.add(task);
            }

        } catch (SQLException e) {
        }
        return tasks;
    }

    public static List<Task> findDepartTasks(String department) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEPART_TASKS_STMT);) {
            preparedStatement.setString(1, department);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String task_desc = rs.getString("task_desc");
                String status = rs.getString("status");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                int group_num = rs.getInt("group_num");
                String assignee = rs.getString("assignee");
                LocalDate due_date = null;
                if (rs.getString("due_date") != null) {
                    due_date = LocalDate.parse(rs.getString("due_date"));
                }
                Task task = new Task(id, assignee, task_desc, status, date_added_on, due_date, group_num, department);
                tasks.add(task);
            }

        } catch (SQLException e) {
        }
        return tasks;
    }

    public static List<Task> findAllTasks(String field, String direction) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TASKS_STMT);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String assignee = rs.getString("assignee");
                String task_desc = rs.getString("task_desc");
                String status = rs.getString("status");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                LocalDate due_date = LocalDate.parse(rs.getString("due_date"));
                int group_num = rs.getInt("group_num");
                String department = rs.getString("department");
                Task task = new Task(id, assignee, task_desc, status, date_added_on, due_date, group_num, department);
                tasks.add(task);
            }

        } catch (SQLException e) {
        }
        return tasks;
    }

    public static Task findById(int id) {
        Task task = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_TASK_STMT);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String task_desc = rs.getString("task_desc");
                String assignee = rs.getString("assignee");
                String status = rs.getString("status");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                LocalDate due_date = LocalDate.parse(rs.getString("due_date"));
                int group_num = rs.getInt("group_num");
                String department = rs.getString("department");
                task = new Task(id, assignee, task_desc, status, date_added_on, due_date, group_num, department);
            }
        } catch (SQLException e) {
        }
        return task;
    }

    public static boolean insertTask(Task task) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_STMT);) {
            preparedStatement.setString(1, task.getAssignee());
            preparedStatement.setString(2, task.getDepartment());
            preparedStatement.setInt(3, task.getGroup_num());
            preparedStatement.setString(4, task.getTask_desc());
            preparedStatement.setString(5, task.getStatus());
            preparedStatement.setString(6, task.getDue_date().toString());
            preparedStatement.setString(7, task.getDate_added_on().toString());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowInserted;
    }

    public static boolean assignTask(int task_id, String assignee) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN_TASK_STMT);) {
            preparedStatement.setString(1, assignee);
            preparedStatement.setInt(2, task_id);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return rowUpdated;
    }

    public static boolean markTask(int task_id, String status) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(MARK_TASK_STMT);) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, task_id);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return rowUpdated;
    }
}
