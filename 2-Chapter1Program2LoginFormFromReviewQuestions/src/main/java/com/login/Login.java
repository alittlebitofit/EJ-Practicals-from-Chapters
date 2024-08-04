package com.login;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class Login extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		PrintWriter out = resp.getWriter();
		
		if(!username.equals("") && !password.equals(""))
		{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chapters_db", "root", "root");
				PreparedStatement ps = con.prepareStatement("SELECT * FROM usersdata WHERE username=? AND password=?");
				ps.setString(1, username);
				ps.setString(2, password);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.isBeforeFirst())
				{
					out.println("<h1 style='color:green;'>Login Success</h1>");
				}
				else
				{
					out.println("<h1 style='color:red;'>Login Failure</h1>");
				}
				
				rs.close();
				ps.close();
				con.close();
				out.close();
				
			}
			catch(Exception e)
			{
				out.println("<h1 style='color:red;'>" + e.getMessage() + "</h1>");
			}
		}
		else
		{
			out.println("<h1 style='color:red;'>Fields cannot be empty</h1>");
		}
		
	}

}
