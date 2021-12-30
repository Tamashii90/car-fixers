package com.WJD.dao;

import java.util.Date;
import com.WJD.model.Comment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private static final String DB_URI = System.getenv("WJD_DB_URI");
    private static final String DB_USER = System.getenv("WJD_DB_USERNAME");
    private static final String DB_PWD = System.getenv("WJD_DB_PASSWORD");

    private static final String FIND_TASK_COMMENTS_STMT = "SELECT * FROM comments WHERE task_id = ?";
    private static final String INSERT_COMMENT_STMT = "INSERT INTO comments (comment_desc, author, date_added_on,task_id) VALUES (?, ?, ?, ?);";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return connection;
    }

    public static List<Comment> findTaskComments(int task_id) {

        List<Comment> comments = new ArrayList<>();
        // this is called try-with-resource, it will auto-close the connection.
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_TASK_COMMENTS_STMT);) {
            preparedStatement.setInt(1, task_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String author = rs.getString("author");
                String comment_desc = rs.getString("comment_desc");
                LocalDate date_added_on = LocalDate.parse(rs.getString("date_added_on"));
                Comment comment = new Comment(id, task_id, author, comment_desc, date_added_on);
                comments.add(comment);
            }

        } catch (SQLException e) {
        }
        return comments;
    }
    
     public static boolean insertComment(Comment comment) {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT_STMT);) {
            preparedStatement.setString(1, comment.getComment_desc());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getDate_added_on().toString());
            preparedStatement.setInt(4, comment.getTask_id());
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowInserted;
    }
}
