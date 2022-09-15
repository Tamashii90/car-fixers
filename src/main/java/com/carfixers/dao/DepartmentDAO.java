package com.carfixers.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_DEP_BY_GROUP =
            "SELECT DISTINCT DEP_ID " +
            "FROM departments JOIN groups USING (DEP_ID) " +
            "WHERE GROUP_ID = ?;";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static int findDepIdByGroupId(int group_id) {
        int dep_id = -123; // random value that can't match a real dep_id
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEP_BY_GROUP);) {
            preparedStatement.setInt(1, group_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dep_id = rs.getInt("DEP_ID");
            }
        } catch (SQLException e) {
        }
        return dep_id;
    }
}
