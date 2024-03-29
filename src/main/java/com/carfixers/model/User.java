package com.carfixers.model;

public class User {

    private int emp_id;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String role;
    private int group_id;
    private int dep_id;
    String group_name;
    String dep_name;

    public User(int emp_id, String username, String password, String role, int group_id, String group_name, int dep_id, String dep_name) {
        this.emp_id = emp_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.group_id = group_id;
        this.dep_id = dep_id;
        this.group_name = group_name;
        this.dep_name = dep_name;
    }

    public User(String username, String first_name, String last_name, String password, String role, String group_name) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;
        this.group_name = group_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "emp_id=" + emp_id +
                ", username='" + username + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", group_id=" + group_id +
                ", dep_id=" + dep_id +
                ", group_name='" + group_name + '\'' +
                ", dep_name='" + dep_name + '\'' +
                '}';
    }
}