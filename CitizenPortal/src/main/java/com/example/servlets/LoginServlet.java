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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Print email and password for debugging
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Validate email and password formats
        if (!isValidEmail(email)) {
            response.getWriter().println("Invalid email format.");
            return;
        }

        if (!isValidPassword(password)) {
            response.getWriter().println("Invalid password. Password must be at least 8 characters long, contain at least one number, and one special character.");
            return;
        }

        // Validate credentials against the database
        try (Connection connection = DBUtil.getConnection())
        {
        if ("admin@gmail.com".equals(email) && "Admin@123".equals(password)) {
            response.sendRedirect("admin-home.html");
            return;
            }
        {
            String sql = "SELECT * FROM users WHERE email = ? AND password = MD5(?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Login successful, redirect to the index page
                        response.sendRedirect("index.html");
                    } else {
                        // Login failed, show error message
                        response.getWriter().println("Invalid email or password.");
                    }
                }
            }
        }
        }
        catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error.");
        }
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
