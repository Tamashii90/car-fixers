package com.carfixers.model;

import java.time.LocalDate;

public class Comment {

    private int id;
    private int task_id;
    private int authorId;
    private String comment_desc;
    private LocalDate date_added_on;

    public Comment(int task_id, int authorId, String comment_desc) {
        this.task_id = task_id;
        this.authorId = authorId;
        this.comment_desc = comment_desc;
        this.date_added_on = LocalDate.now();
    }
    
    public Comment(int id, int task_id, int authorId, String comment_desc, LocalDate date_added_on) {
        this.id = id;
        this.task_id = task_id;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

}