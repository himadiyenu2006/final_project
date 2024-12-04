<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Salary Management</title>
</head>
<body>
<h1>Manage Salaries</h1>
<form action="updateSalary" method="post">
    <label for="employeeId">Employee ID:</label>
    <input type="text" id="employeeId" name="employeeId" required><br>

    <label for="amount">Salary Amount:</label>
    <input type="number" id="amount" name="amount" required><br>

    <input type="submit" value="Update Salary">
</form>
<a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
