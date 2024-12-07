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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usernameToDelete = request.getParameter("deleteUser");

        if (usernameToDelete != null && !usernameToDelete.isEmpty()) {
            try (Connection connection = DBUtil.getConnection()) {
                String sql = "DELETE FROM users WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, usernameToDelete);
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        response.sendRedirect("manage_users.html?status=success");
                    } else {
                        response.sendRedirect("manage_users.html?status=failure");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("manage_users.html?status=error");
            }
        } else {
            response.sendRedirect("manage_users.html?status=invalid");
        }
    }
}
