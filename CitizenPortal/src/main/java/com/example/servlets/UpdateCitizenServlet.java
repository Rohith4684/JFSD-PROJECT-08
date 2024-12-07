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

public class UpdateCitizenServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String politicianId = request.getParameter("politician_id");
        String updateDescription = request.getParameter("update_description");

        // Validation: Check if input is not null or empty
        if (politicianId == null || politicianId.trim().isEmpty() || updateDescription == null || updateDescription.trim().isEmpty()) {
            response.getWriter().println("Politician ID and update description are required.");
            return;
        }

        try (Connection connection = DBUtil.getConnection()) {
            // SQL query to insert the update into the database
            String sql = "INSERT INTO updates (politician_id, update_description) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, politicianId);
                statement.setString(2, updateDescription);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.sendRedirect("update-success.html"); // Redirect to a success page after successful update
                } else {
                    response.getWriter().println("Failed to insert update. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error. Please try again later.");
        }
    }
}
