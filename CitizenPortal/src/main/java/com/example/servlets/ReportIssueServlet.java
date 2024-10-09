package com.example.servlets;
import com.example.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReportIssueServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String issueDescription = request.getParameter("issue_description");
        int politicianId = Integer.parseInt(request.getParameter("politician_id"));

        try (Connection connection = DBUtil.getConnection()) {
            String sql = "INSERT INTO issues (username, issue_description, politician_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, issueDescription);
                statement.setInt(3, politicianId);
                statement.executeUpdate();
                response.sendRedirect("issueSubmitted.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error.");
        }
    }
}
