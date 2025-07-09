package com.nhom9.models;

public class User {
    private String phone;
    private String email;
    private String password;
    private String username;

    public User() {
    }

    public User(String phone, String email, String password) {
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    // ✅ GETTER & SETTER MỚI
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Các getter & setter có sẵn
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
