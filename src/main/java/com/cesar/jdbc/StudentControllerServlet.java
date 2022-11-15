package com.cesar.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentdbutil;
	@Resource(name="jdbc/web_student_tracker") //To inject the DataSource--->Conn pool / DataSource
	private DataSource dataSource;
	//Initialize the init method when the servlet gets executed
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		//Create StudentDBUtil and pass Conn pool / DataSource
		try {
			studentdbutil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServletException(e);//In case de DB is not runnung.
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//To hanfle the request coming in.
		//List the students in MVC fashion.
		try {
			//Read the command parameter.
			String theCommand = request.getParameter("command");
			if(theCommand == null){
				theCommand = "LIST";
			}//
			//Route to the appropiate method.
			switch(theCommand) {
				case "LIST":
					listStudents(request, response);
					break;
				case "ADD":
					addStudent(request, response);
					break;
				case "LOAD":
					loadStudent(request, response);
					break;
				case "UPDATE":
					updateStudent(request, response);
					break;
				case "DELETE":
					deleteStudent(request, response);
					break;
				default:
					listStudents(request, response);
			}//
			//listStudents(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		} 
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//Read student id from the form data
		String theStudentId = request.getParameter("studentId");
		//Delete student from DB
		studentdbutil.deleteStudent(theStudentId);
		//Senf the user back to the list students page
		listStudents(request, response);
		
	}
	private void updateStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//Read the Student info from the form data.
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		//Create new student object.
		Student theStudent = new Student(id, firstName, lastName, email);
		//Perform update on DB.
		studentdbutil.updateStudent(theStudent);
		//Send them back to the "list Student" page.
		listStudents(request, response);
		
	}
	private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//Read the student id from the form data.
		String theStudentId = request.getParameter("studentId");
		//Get the student from DB(db Util).
		Student theStudent = studentdbutil.getStudent(theStudentId);
		//Place the student in the request attribute.
		request.setAttribute("THE_STUDENT", theStudent);
		//Send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		//Read student info from the form data.
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		//Create a new student object.
		Student theStudent = new Student(firstName, lastName, email);
		//Add the student to the DB.
		studentdbutil.addStudent(theStudent);
		//Send back to the main page(the student list).
		listStudents(request, response);
		
	}
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// TODO Auto-generated method stub
		//Get the students from DB,
		List<Student>students = studentdbutil.getStudents();
		//Add students to the request.
		request.setAttribute("STUDENT_LIST", students);
		//Send to JSP page(view).
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
