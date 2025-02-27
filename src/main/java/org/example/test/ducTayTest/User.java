package org.example.test.ducTayTest;

public class User {
    private final String id;
    private String username;
    private String password;
    private String fullName;
    private String role;

    public User(String id, String username, String password, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}