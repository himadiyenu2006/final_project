<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
</head>
<body>
<h1>Manage Customers</h1>
<form action="addCustomer" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="contact">Contact:</label>
    <input type="text" id="contact" name="contact" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="address">Address:</label>
    <input type="text" id="address" name="address" required><br>

    <input type="submit" value="Add Customer">
</form>
<a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
