package com.carfixers.model;

import java.time.LocalDate;

public class Task {

    private int id;
    private int emp_id;
    private String assignee;
    private String task_desc;
    private String status;
    private LocalDate date_added_on;
    private LocalDate due_date;

    public Task(int emp_id, String task_desc, String status, LocalDate due_date) {
        this.emp_id = emp_id;
        this.task_desc = task_desc;
        this.status = status;
        this.due_date = due_date;
        this.date_added_on = LocalDate.now();
    }

    public Task(int id, int emp_id, String task_desc, String status, LocalDate date_added_on, LocalDate due_date) {
        this.id = id;
        this.emp_id = emp_id;
        this.task_desc = task_desc;
        this.status = status;
        this.date_added_on = date_added_on;
        this.due_date = due_date;
    }

    public Task(int id, String assignee, String task_desc, String status, LocalDate date_added_on, LocalDate due_date) {
        this.id = id;
        this.assignee = assignee;
        this.task_desc = task_desc;
        this.status = status;
        this.date_added_on = date_added_on;
        this.due_date = due_date;
    }

    public Task(String assignee, String task_desc, String status, LocalDate date_added_on, LocalDate due_date) {
        this.assignee = assignee;
        this.task_desc = task_desc;
        this.status = status;
        this.date_added_on = date_added_on;
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate_added_on() {
        return date_added_on;
    }

    public void setDate_added_on(LocalDate date_added_on) {
        this.date_added_on = date_added_on;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", assignee=" + assignee + ", task_desc=" + task_desc + ", status=" + status + ", date_added_on=" + date_added_on + ", due_date=" + due_date+" }";
    }
}
