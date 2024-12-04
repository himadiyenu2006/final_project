<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Department Management</title>
</head>
<body>
<h1>Manage Departments</h1>
<form action="addDepartment" method="post">
    <label for="name">Department Name:</label>
    <input type="text" id="name" name="name" required><br>

    <input type="submit" value="Add Department">
</form>
<a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
