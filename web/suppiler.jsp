<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Supplier Management</title>
</head>
<body>
<h1>Manage Suppliers</h1>
<form action="addSupplier" method="post">
    <label for="name">Supplier Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="contact">Contact Info:</label>
    <input type="text" id="contact" name="contact" required><br>

    <input type="submit" value="Add Supplier">
</form>
<a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
