package com.sumofdigits;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import java.io.PrintWriter;

@WebServlet("/sumofdigits")
public class SumOfDigits extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String numString = req.getParameter("num");

		if (!numString.equals("")) {

			int num = 0;

			try {
				num = Integer.parseInt(numString);
			} catch (Exception e) {
				out.println("<h1 style='color:maroon;'>Invalid number</h1>");
			}

			int ogNum = num;
			int sum = 0;

			while (num > 0) {
				int remainder = num % 10;
				sum += remainder;
				num /= 10;
			}

			out.println("<h2>The sum of digits of " + ogNum + " is " + sum + ".</h2>");
			
		} else {
			out.println("<h2>Enter a number bro</h2>");
		}
		
		out.close();
	}

}
