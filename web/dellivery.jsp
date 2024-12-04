<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delivery Management</title>
</head>
<body>
<h1>Manage Deliveries</h1>
<form action="updateDelivery" method="post">
    <label for="orderId">Order ID:</label>
    <input type="text" id="orderId" name="orderId" required><br>

    <label for="status">Delivery Status:</label>
    <select id="status" name="status" required>
        <option value="Pending">Pending</option>
        <option value="In Transit">In Transit</option>
        <option value="Delivered">Delivered</option>
    </select><br>

    <input type="submit" value="Update Delivery Status">
</form>
<a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
