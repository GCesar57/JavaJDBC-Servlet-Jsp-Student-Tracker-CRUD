<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
<%-- <%= theStudents %> --%>
<div id="wrapper">
	<div id="header">
		<h2>MyStudents University.</h2>
	</div>
	<!--  -->
	<div id="container">
		<div id="content">
		<!-- ADD BUTTON -->
		<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" class="add-student-button">
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="temp" items="${STUDENT_LIST}">
					<!-- Define the links here -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"></c:param>
						<c:param name="studentId" value="${temp.id }"></c:param>
					</c:url>
					<!-- link to delete student -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"></c:param>
						<c:param name="studentId" value="${temp.id }"></c:param>
					</c:url>
					<tr>
						<td>${temp.firstName }</td>
						<td>${temp.lastName }</td>
						<td>${temp.email }</td>
						<td><a href="${tempLink}">Update</a>| <a href="${deleteLink}" onclick="if(!(confirm('Delete Student?')))return false" >Delete</a>
						</td>
					</tr>
				</c:forEach>
				<!--  -->
			</table>
		</div>
	</div>
</div>
</body>
</html> 