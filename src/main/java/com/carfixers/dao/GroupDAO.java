package com.carfixers.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_GROUP_BY_TASK =
            "SELECT GROUP_ID " +
            "FROM tasks JOIN employees USING (EMP_ID) " +
            "WHERE TASK_ID = ?;";

    private static final String FIND_GROUPS_BY_DEP =
            "SELECT GROUP_NAME " +
            "FROM groups JOIN departments USING (DEP_ID) " +
            "WHERE DEP_NAME = ? AND GROUP_NAME NOT LIKE '%head%';";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static int findGroupIdByTaskId(int task_id) {
        int group_id = -123; // random value that can't match a real group_id
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_GROUP_BY_TASK);) {
            preparedStatement.setInt(1, task_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                group_id = rs.getInt("GROUP_ID");
            }
        } catch (SQLException e) {
        }
        return group_id;
    }

    public static List<String> findGroupsByDepartment(String department) {
        List<String> groups = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_GROUPS_BY_DEP);) {
            preparedStatement.setString(1, department);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String group = rs.getString("GROUP_NAME");
                groups.add(group);
            }

        } catch (SQLException e) {
        }
        return groups;
    }
}
