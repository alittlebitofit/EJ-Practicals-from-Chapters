package com.employees;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CreateNewEmployee")
public class InsertNewEmployees extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String empID = req.getParameter("empID");
		String empName = req.getParameter("empName");
		String empDept = req.getParameter("empDept");
		String empDesig = req.getParameter("empDesig");
		
		if(!empID.equals("") && !empName.equals("") && !empDept.equals("") && !empDesig.equals("")) {
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chapters_db", "root", "root");
				PreparedStatement ps;
				DatabaseMetaData dbmd = con.getMetaData();
				ResultSet rs = dbmd.getTables(null, null, "Employee" , null);
				
				if(!rs.isBeforeFirst()) {
					ps = con.prepareStatement("CREATE TABLE Employee(EmpID VARCHAR(100) PRIMARY KEY, "
							+ "EmpName VARCHAR(100), EmpDept VARCHAR(100), EmpDesig VARCHAR(100));");
					ps.executeUpdate();
				}
				
				ps = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?)");
				ps.setString(1, empID);
				ps.setString(2, empName);
				ps.setString(3, empDept);
				ps.setString(4, empDesig);
				
				int row = ps.executeUpdate();
				
				if(row > 0) {
					out.println("<h1>Succesfully added " + empName + " into database.</h1>");
				} else {
					out.println("<h1>" + empName + " could not be added into database.</h1>");
				}
				
				con.close();
				ps.close();
				rs.close();
				
			} catch(Exception e) {
				out.println("<h1>Exception: " + e.getMessage() + "</h1>");
			}
			
			
		} else {
			out.println("<h2>Enter data bro</h2>");
		}
		
		out.close();
		
	}

}
