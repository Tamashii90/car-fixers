package com.carfixers.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDAO {
    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_DEP_BY_GROUP =
            "SELECT DISTINCT DEP_ID " +
            "FROM departments JOIN groups USING (DEP_ID) " +
            "WHERE GROUP_ID = ?;";
    public static final String FIND_DEPS_AND_GROUPS =
            "SELECT DEP_NAME, GROUP_NAME " +
            "FROM departments JOIN groups USING (DEP_ID) " +
            "WHERE DEP_NAME != 'ceo' AND GROUP_NAME NOT LIKE '%head%';";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static Map<String, List<String>> findAllDepsAndGroups() {
        Map<String, List<String>> map = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEPS_AND_GROUPS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String depName = rs.getString("DEP_NAME");
                String groupName = rs.getString("GROUP_NAME");
                if (!map.containsKey(depName)) map.put(depName, new ArrayList<>());
                map.get(depName).add(groupName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return map;
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
