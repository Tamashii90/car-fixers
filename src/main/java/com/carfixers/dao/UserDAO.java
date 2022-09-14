package com.carfixers.dao;

import com.carfixers.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

//    private static final String FIND_DEP_GROUPS =
//            "SELECT GROUP_NAME " +
//            "FROM groups JOIN departments USING (DEP_ID) " +
//            "WHERE DEP_NAME = ?;";
    private static final String FIND_ASSIGNABLE_USERS_BY_TASK =
            "SELECT EMP_NNAME " +
            "FROM employees WHERE EMP_ROLE = 'group_member' AND GROUP_ID = (SELECT GROUP_ID " +
            "FROM tasks JOIN employees USING (EMP_ID) " +
            "WHERE TASK_ID = ?);";
    private static final String FIND_EMP_BY_NICKNAME = "SELECT * FROM employees WHERE EMP_NNAME = ?;";
    private static final String FIND_ALL_STMT = "SELECT * FROM users;";

    private static final String INSERT_USER_STMT =
            "INSERT INTO employees (`EMP_ID`, `EMP_NNAME`, `EMP_PWD`, `EMP_FNAME`, `EMP_LNAME`, `GROUP_ID`, `EMP_ROLE`) " +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?);";


    protected static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        return connection;
    }

    //    public static List<User> findAllUsers() {
//
//        ArrayList<User> users = new ArrayList<>();
//        // this is called try-with-resource, it will auto-close the connection.
//        try (Connection connection = getConnection();
//                Statement stmt = connection.createStatement();) {
//            ResultSet rs = stmt.executeQuery(FIND_ALL_STMT);
//            while (rs.next()) {
//                int id = rs.getInt(1);
//                String name = rs.getString(2);
//                String pwd = rs.getString(3);
////                User user = new User(id, name, pwd);
////                users.add(user);
//            }
//
//        } catch (SQLException e) {
//        }
//
//        return users;
//    }
    public static List<String> findAssignableUsers(int taskId) {

        List<String> usernames = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ASSIGNABLE_USERS_BY_TASK);) {
            preparedStatement.setInt(1, taskId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                usernames.add(username);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usernames;
    }

    public static User findEmpByNickname(String nickname) throws Exception {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_EMP_BY_NICKNAME);) {
            preparedStatement.setString(1, nickname);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int group_num = rs.getInt("GROUP_ID");
                // use database username to avoid case-sensitivity errors
                String username = rs.getString("EMP_NNAME");
                String password = rs.getString("EMP_PWD");
                String role = rs.getString("EMP_ROLE");
                user = new User(username, password, role, group_num);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return user;
    }

//    public static List<String> findDepGroups(String department) {
//
//        List<String> groupNames = new ArrayList<>();
//        // this is called try-with-resource, it will auto-close the connection.
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEP_GROUPS);) {
//            preparedStatement.setString(1, department);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                String groupName = rs.getString("GROUP_NAME");
//                groupNames.add(groupName);
//            }
//        } catch (Exception e) {
//        }
//        return groupNames;
//    }

    public static boolean insertUser(User user) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_STMT);) {
            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, hashed);
            preparedStatement.setInt(5, user.getGroup_id());
            preparedStatement.setString(6, user.getRole());
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return rowInserted;
    }

}
