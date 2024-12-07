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


public class SubmitFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form
        String politicianId = request.getParameter("politician-id");
        String feedback = request.getParameter("feedback");

        try (Connection connection = DBUtil.getConnection()) {
            // SQL query to insert feedback into the database
            String sql = "INSERT INTO feedback (politician_id, feedback_text) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, politicianId);
                statement.setString(2, feedback);
                statement.executeUpdate();

                // Redirect to a success page after submission
                response.sendRedirect("feedbackSubmitted.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error. Please try again later.");
        }
    }
}
