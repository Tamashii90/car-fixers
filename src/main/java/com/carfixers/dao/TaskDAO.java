package com.carfixers.dao;

import com.carfixers.model.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_TASK_STMT =
            "SELECT TASK_ID, EMP_NNAME, TASK_DESC, TASK_STATUS, DEP_NAME, GROUP_NAME, TASK_CRTDATE, TASK_DUEDATE " +
            "FROM tasks JOIN employees USING (EMP_ID) JOIN groups USING (GROUP_ID) JOIN departments USING (DEP_ID) " +
            "WHERE TASK_ID = ?";
    private static final String FIND_ALLOWED_TASKS_STMT =
            "SELECT TASK_ID, EMP_NNAME, TASK_DESC, TASK_STATUS, DEP_NAME, GROUP_NAME, TASK_CRTDATE, TASK_DUEDATE " +
            "FROM tasks JOIN employees USING (EMP_ID) JOIN groups USING (GROUP_ID) JOIN departments USING (DEP_ID) " +
            "WHERE EMP_NNAME = ? OR (TASK_STATUS = 'available' AND GROUP_ID = ?);";
    private static final String FIND_GROUP_TASKS_STMT =
            "SELECT TASK_ID, EMP_NNAME, TASK_DESC, TASK_STATUS, DEP_NAME, GROUP_NAME, TASK_CRTDATE, TASK_DUEDATE " +
            "FROM tasks JOIN employees USING (EMP_ID) JOIN groups USING (GROUP_ID) JOIN departments USING (DEP_ID) " +
            "WHERE GROUP_ID = ?;";
    private static final String FIND_DEPART_TASKS_STMT =
            "SELECT TASK_ID, EMP_NNAME, TASK_DESC, TASK_STATUS, DEP_NAME, GROUP_NAME, TASK_CRTDATE, TASK_DUEDATE " +
            "FROM tasks JOIN employees USING (EMP_ID) JOIN groups USING (GROUP_ID) JOIN departments USING (DEP_ID) " +
            "WHERE DEP_NAME = ?;";
    private static final String FIND_ALL_TASKS_STMT =
            "SELECT TASK_ID, EMP_NNAME, TASK_DESC, TASK_STATUS, DEP_NAME, GROUP_NAME, TASK_CRTDATE, TASK_DUEDATE " +
            "FROM tasks JOIN employees USING (EMP_ID) JOIN groups USING (GROUP_ID) JOIN departments USING (DEP_ID);";
    private static final String INSERT_TASK_STMT =
            "INSERT INTO `tasks` (`EMP_ID`, `TASK_DESC`, `TASK_STATUS`, `TASK_DUEDATE`, `TASK_CRTDATE`) " +
            "VALUES (?, ?, ?, ?, ?);";
    private static final String ASSIGN_TASK_STMT =
            "UPDATE tasks SET EMP_ID = (SELECT EMP_ID FROM employees WHERE EMP_NNAME = ?), TASK_STATUS = 'assigned' " +
            "WHERE TASK_ID = ?;";
    private static final String MARK_TASK_STMT =
            "UPDATE tasks SET TASK_STATUS = ? " +
            "WHERE TASK_ID = ?;";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static List<Task> findAllowedTasks(String username, int groupId) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALLOWED_TASKS_STMT);) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, groupId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("TASK_ID");
                // because assignee might be null, don't use assignee from above
                String realAssignee = rs.getString("EMP_NNAME");
                String task_desc = rs.getString("TASK_DESC");
                String status = rs.getString("TASK_STATUS");
                String group_name = rs.getString("GROUP_NAME");
                String dep_name = rs.getString("DEP_NAME");
                LocalDate date_added_on = LocalDate.parse(rs.getString("TASK_CRTDATE"));
                LocalDate due_date = LocalDate.parse(rs.getString("TASK_DUEDATE"));
                Task task = new Task(id, realAssignee, task_desc, status, group_name, dep_name, date_added_on, due_date);
                tasks.add(task);
            }

        } catch (SQLException e) {
        }
        return tasks;
    }

    public static List<Task> findGroupTasks(int group_id) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_GROUP_TASKS_STMT);) {
            preparedStatement.setInt(1, group_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("TASK_ID");
                String task_desc = rs.getString("TASK_DESC");
                String status = rs.getString("TASK_STATUS");
                LocalDate date_added_on = LocalDate.parse(rs.getString("TASK_CRTDATE"));
                LocalDate due_date = LocalDate.parse(rs.getString("TASK_DUEDATE"));
                String assignee = rs.getString("EMP_NNAME");
                String group_name = rs.getString("GROUP_NAME");
                String dep_name = rs.getString("DEP_NAME");
                Task task = new Task(id, assignee, task_desc, status, group_name, dep_name, date_added_on, due_date);
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
                int id = rs.getInt("TASK_ID");
                String task_desc = rs.getString("TASK_DESC");
                String status = rs.getString("TASK_STATUS");
                LocalDate date_added_on = LocalDate.parse(rs.getString("TASK_CRTDATE"));
                String assignee = rs.getString("EMP_NNAME");
                LocalDate due_date = null;
                String group_name = rs.getString("GROUP_NAME");
                String dep_name = rs.getString("DEP_NAME");
                if (rs.getString("TASK_DUEDATE") != null) {
                    due_date = LocalDate.parse(rs.getString("TASK_DUEDATE"));
                }
                Task task = new Task(id, assignee, task_desc, status, group_name, dep_name, date_added_on, due_date);
                tasks.add(task);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public static List<Task> findAllTasks(String orderField, String direction) {

        List<Task> tasks = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TASKS_STMT);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("TASK_ID");
                String task_desc = rs.getString("TASK_DESC");
                String status = rs.getString("TASK_STATUS");
                LocalDate date_added_on = LocalDate.parse(rs.getString("TASK_CRTDATE"));
                LocalDate due_date = LocalDate.parse(rs.getString("TASK_DUEDATE"));
                String assignee = rs.getString("EMP_NNAME");
                String group_name = rs.getString("GROUP_NAME");
                String dep_name = rs.getString("DEP_NAME");
                Task task = new Task(id, assignee, task_desc, status, group_name, dep_name, date_added_on, due_date);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                String task_desc = rs.getString("TASK_DESC");
                String status = rs.getString("TASK_STATUS");
                LocalDate date_added_on = LocalDate.parse(rs.getString("TASK_CRTDATE"));
                LocalDate due_date = LocalDate.parse(rs.getString("TASK_DUEDATE"));
                String assignee = rs.getString("EMP_NNAME");
                String dep_name = rs.getString("DEP_NAME");
                String group_name = rs.getString("GROUP_NAME");
                task = new Task(id, assignee, task_desc, status, group_name, dep_name, date_added_on, due_date);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    public static boolean insertTask(Task task) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_STMT);) {
            preparedStatement.setInt(1, task.getEmp_id());
            preparedStatement.setString(2, task.getTask_desc());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setString(4, task.getDue_date().toString());
            preparedStatement.setString(5, task.getDate_added_on().toString());

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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return rowUpdated;
    }
}
