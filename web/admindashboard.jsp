<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Admin Dashboard</h1>

<h2>Current Orders</h2>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Customer Name</th>
        <th>Product</th>
        <th>Quantity</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <!-- Sample Data - This should be dynamically generated with JSP -->
    <tr>
        <td>1</td>
        <td>John Doe</td>
        <td>Margherita</td>
        <td>2</td>
        <td>Pending</td>
        <td><a href="updateOrder?id=1">Update</a></td>
    </tr>
    <tr>
        <td>2</td>
        <td>Jane Smith</td>
        <td>Pepperoni</td>
        <td>1</td>
        <td>Completed</td>
        <td><a href="updateOrder?id=2">Update</a></td>
    </tr>
    <!-- More orders would be populated here from the database -->
</table>

<br>
<h2>Inventory Management</h2>
<p>Click <a href="manageInventory.jsp">here</a> to manage inventory.</p>

<br>
<a href="index.jsp">Back to Home</a>
</body>
</html>
