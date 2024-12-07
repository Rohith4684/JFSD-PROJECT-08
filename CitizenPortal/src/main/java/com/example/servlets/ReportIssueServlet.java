package com.example.servlets;

import com.example.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig
public class ReportIssueServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String issueDescription = request.getParameter("issue_description");
        int politicianId = Integer.parseInt(request.getParameter("politician_id"));

        // File upload processing
        Part filePart = request.getPart("file"); // Retrieves the file part
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        // Ensure upload directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save the file to disk
        String filePath = uploadPath + File.separator + fileName;
        try (InputStream fileContent = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        // Store issue details and file path in the database
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "INSERT INTO issues (username, issue_description, politician_id, file_path) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, issueDescription);
                statement.setInt(3, politicianId);
                statement.setString(4, filePath);
                statement.executeUpdate();
                response.sendRedirect("issueSubmitted.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error.");
        }
    }
}
