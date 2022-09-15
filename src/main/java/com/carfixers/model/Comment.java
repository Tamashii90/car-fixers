package com.carfixers.model;

import java.time.LocalDate;

public class Comment {

    private int id;
    private int task_id;
    private int emp_id;
    private String author;
    private String comment_desc;
    private LocalDate date_added_on;

    public Comment(int task_id, int emp_id, String comment_desc) {
        this.task_id = task_id;
        this.emp_id = emp_id;
        this.comment_desc = comment_desc;
        this.date_added_on = LocalDate.now();
    }

    public Comment(int id, int task_id, String author, String comment_desc, LocalDate date_added_on) {
        this.id = id;
        this.task_id = task_id;
        this.author = author;
        this.comment_desc = comment_desc;
        this.date_added_on = date_added_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getAuthorId() {
        return author;
    }

    public void setAuthorId(String author) {
        this.author = author;
    }

    public String getComment_desc() {
        return comment_desc;
    }

    public void setComment_desc(String comment_desc) {
        this.comment_desc = comment_desc;
    }

    public LocalDate getDate_added_on() {
        return date_added_on;
    }

    public void setDate_added_on(LocalDate date_added_on) {
        this.date_added_on = date_added_on;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", task_id=" + task_id +
                ", emp_id=" + emp_id +
                ", author='" + author + '\'' +
                ", comment_desc='" + comment_desc + '\'' +
                ", date_added_on=" + date_added_on +
                '}';
    }
}