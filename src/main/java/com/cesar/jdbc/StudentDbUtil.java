package com.cesar.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private DataSource dataSource;
	//
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}//
	//
	//Method to list all the students
	public List<Student>getStudents() throws Exception{
		//Return the Students from the DB and return it
		List<Student>students = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			//Get the connection
			myConn = dataSource.getConnection();
			//Create SQL Statement
			String sql = "SELECT * FROM student ORDER BY last_name";
			myStmt = myConn.createStatement();
			//Execute the query
			myRs = myStmt.executeQuery(sql);
			//Process the Result sets
			while(myRs.next()) {
				//Retrieve data from the ResultSet
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				//Create a new Student Object
				Student tempStudent = new Student(id, firstName, lastName, email);
				//Add it to the list of students
				students.add(tempStudent);
			}//
			return students;
		} finally {
			//Close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}//
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs !=null) {
				myRs.close();
			}//
			if(myStmt !=null) {
				myStmt.close();
			}//
			if(myConn !=null) {
				myConn.close();
			}//
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.getStackTrace();
		}
	}
	public void addStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			//Get the connection.
			myConn = dataSource.getConnection();
		    //Create sql for the insert.
			String sql = "INSERT INTO student(first_name, last_name, email)VALUES(?, ?, ?)";
			//Set the param values for the student.
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			//Execute sql insert
			myStmt.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.getStackTrace();
		}finally {
			//Clean up JDBC objects.
			close(myConn, myStmt, null); //Null is for ResultSet
		}//Finally
		
	}
	public Student getStudent(String theStudentId)throws Exception {
		// TODO Auto-generated method stub
		Student theStudent = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			//Convert student id to int
			studentId = Integer.parseInt(theStudentId);
			//Get the connection to DB
			myConn = dataSource.getConnection();
			//Create sql to get selected student
			String sql = "SELECT * FROM student WHERE id=?";
			//create prepare statement
			myStmt = myConn.prepareStatement(sql);
			//set params
			myStmt.setInt(1, studentId);
			//execute statement
			myRs = myStmt.executeQuery();
			//Retrieve data from result set row
			if(myRs.next()){
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				//Use the studentId during construction
				theStudent = new Student(studentId, firstName, lastName, email);
			}else {
				throw new Exception("Could not find any student with id "+studentId);
			}//
			return theStudent;
		} finally {
			//Clean up JDBC object
			close(myConn, myStmt, myRs);
		}
	}
	public void updateStudent(Student theStudent)throws Exception {
		//
		Connection myConn= null;
		PreparedStatement myStmt = null;
		try {
			//Get the connection to the DB.
			myConn = dataSource.getConnection();
			//Create SQL update statement.
			String sql = "UPDATE student SET first_name=?, last_name=?, email=? WHERE id=?";
			//Prepare statement
			myStmt = myConn.prepareStatement(sql);
			//Set params.
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			//Execute SQL statement.
			myStmt.execute();
		} finally {
			//
			close(myConn, myStmt, null);
		}
	}
	public void deleteStudent(String theStudentId)throws Exception {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			//Convert student id to int
			int studentId = Integer.parseInt(theStudentId);
			//Get the connection to the DB
			myConn = dataSource.getConnection();
			//Create sql to delete student
			String sql = "DELETE FROM student WHERE id=?";
			//Prepare statement
			myStmt = myConn.prepareStatement(sql);
			//set params
			myStmt.setInt(1, studentId);
			//Execute sql statement
			myStmt.execute();
		} finally {
			//Clean up the JDBC code
			close(myConn, myStmt, null);
		}
	}
}
