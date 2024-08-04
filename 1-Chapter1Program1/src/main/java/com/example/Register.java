package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String country = req.getParameter("country");

		if (!username.equals("") && !password.equals("") && !email.equals("") && !country.equals("")) {

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chapters_db", "root", "root");
				PreparedStatement ps = con.prepareStatement("INSERT INTO usersdata VALUES(?,?,?,?);");

				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, country);

				int row = ps.executeUpdate();

				out.println("<h1>" + row + " inserted successfully.</h1>");
			} catch (Exception e) {
				out.println(e.getMessage());
			}
		}

	}

}
