package com.cesar.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//Define Datasource/Connection pool for resource Injection
	@Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Set up the printWritter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		//Set the connection to the DB
		Connection myConn = null;
		Statement mySt = null;
		ResultSet myRs = null;
		try {
			myConn = dataSource.getConnection();
			//Create some SQL Statements
			String sql = "SELECT * FROM student";
			mySt = myConn.createStatement();
			//Execute SQL
			myRs = mySt.executeQuery(sql);
			//Retrieve results
			while(myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
