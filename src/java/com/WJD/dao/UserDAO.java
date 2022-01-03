package com.WJD.dao;

import com.WJD.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_GROUP_NUMS_STMT = "SELECT DISTINCT group_num from groups WHERE department = ?;";
    private static final String FIND_ASSIGNABLE_USERS_STMT = "SELECT username FROM users WHERE department =? AND group_num = ? AND role = 'group_member';";
    private static final String FIND_USER_STMT = "SELECT * FROM users WHERE username = ?;";
    private static final String FIND_ALL_STMT = "SELECT * FROM users;";

    private static final String INSERT_USER_STMT = "INSERT INTO users (first_name, last_name, username, password, department, group_num, role) VALUES (?,?,?,?,?,?,?) ";

    protected static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
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
    public static List<String> findAssignableUsers(String department, int group_num) {

        List<String> usernames = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ASSIGNABLE_USERS_STMT);) {
            preparedStatement.setString(1, department);
            preparedStatement.setInt(2, group_num);
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

    public static User findUser(String username) throws Exception {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_STMT);) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int group_num = rs.getInt("group_num");
                // use database username to avoid case-sensitivity errors
                String dbUsername = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String department = rs.getString("department");
                user = new User(dbUsername, password, role, group_num, department);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return user;
    }

    public static List<Integer> findDepGroups(String department) {

        List<Integer> group_nums = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_GROUP_NUMS_STMT);) {
            preparedStatement.setString(1, department);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int group_num = rs.getInt("group_num");
                group_nums.add(group_num);
            }

        } catch (Exception e) {
        }
        return group_nums;
    }

    public static boolean insertUser(User user) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_STMT);) {
            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, hashed);
            preparedStatement.setString(5, user.getDepartment());
            preparedStatement.setInt(6, user.getGroup_num());
            preparedStatement.setString(7, user.getRole());
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return rowInserted;
    }

}
