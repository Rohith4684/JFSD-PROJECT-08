package com.example.servlets;

public class Issue {
    private int id;
    private String username;
    private String issueDescription;
    private int politicianId;

    // Constructor to initialize all fields
    public Issue(int id, String username, String issueDescription, int politicianId) {
        this.id = id;
        this.username = username;
        this.issueDescription = issueDescription;
        this.politicianId = politicianId;
        }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public int getPoliticianId() {
        return politicianId;
    }

    public void setPoliticianId(int politicianId) {
        this.politicianId = politicianId;
    }
}
