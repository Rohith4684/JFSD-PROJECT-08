package com.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.DBUtil;


@WebServlet("/ViewIssuesadminServlet")
public class ViewIssuesadminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Issue> issueList = new ArrayList<>();
        
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT * FROM issues";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String issueDescription = rs.getString("issue-description");
                int politicianId = rs.getInt("politician-id");
                Issue issue = new Issue(id, username, issueDescription, politicianId);
                issueList.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("issueList", issueList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view_issueadmin.jsp");
        dispatcher.forward(request, response);
    }
}
