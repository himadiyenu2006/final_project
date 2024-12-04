<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory Management</title>
</head>
<body>
<h1>Current Inventory</h1>
<table border="1">
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Quantity in Stock</th>
        <th>Last Updated</th>
    </tr>
    <%
        // Sample data for demonstration
        // In a real application, retrieve this from the database
        String[][] inventory = {
                {"1", "Pepperoni Pizza", "50", "2024-10-01"},
                {"2", "Veggie Pizza", "30", "2024-10-02"},
                {"3", "Chicken Wings", "40", "2024-10-01"},
                {"4", "Caesar Salad", "20", "2024-10-03"}
        };

        for (String[] item : inventory) {
    %>
    <tr>
        <td><%= item[0] %></td>
        <td><%= item[1] %></td>
        <td><%= item[2] %></td>
        <td><%= item[3] %></td>
    </tr>
    <%
        }
    %>
</table>
<a href="index.jsp">Back to Home</a>
</body>
</html>
