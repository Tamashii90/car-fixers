package com.carfixers.model;

public class User {

    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String role;
    private int group_id;

    public User(String username, String password, String role, int group_id) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.group_id = group_id;
    }

    public User(String username, String first_name, String last_name, String password, String role, int group_id) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;
        this.group_id = group_id;
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

}