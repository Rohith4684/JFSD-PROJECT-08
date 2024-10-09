package com.example.servlets;

import com.example.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);

	    try (Connection connection = DBUtil.getConnection()) {
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
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.getWriter().println("Database connection error.");
	    }
	}
}