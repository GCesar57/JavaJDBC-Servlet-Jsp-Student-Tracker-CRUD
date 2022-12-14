<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>

<div id="wrapper">
	<div id="header">
		<h2>MyStudents University.</h2>
	</div>
	<!--  -->
	<div id="container">
		<div id="content">
			<h2>Add new Student</h2>
			<hr>
			<form action="StudentControllerServlet" method="get">
				<input type="hidden" name="command" value="ADD" />
				<table>
					<tbody>
						<tr>
							<!-- First name -->
							<td><label>First Name:</label></td>
							<td><input type="text" name="firstName" /></td>
						</tr>	
						<tr>
							<!-- Last name -->
							<td><label>Last Name:</label></td>
							<td><input type="text" name="lastName" /></td>
						</tr>
						<tr>
							<!-- Email -->
							<td><label>Email:</label></td>
							<td><input type="text" name="email" /></td>
						</tr>
						<tr>
							<!-- Submit -->
							<td><label></label></td>
							<td><input type="submit" value="Save" class="save" /></td>
						</tr>
					</tbody>
				</table>
			</form>
			<div style="clear: both;"></div>
			<p>
				<a href="StudentControllerServlet">Go back to the main list</a>
			</p>
		</div>
	</div>
</div>
</body>
</html>