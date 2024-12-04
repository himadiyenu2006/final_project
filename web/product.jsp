<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>Available Products</h1>
<table border="1">
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Order</th>
    </tr>
    <%
        // Sample data for demonstration
        // In a real application, retrieve this from the database
        String[][] products = {
                {"1", "Pepperoni Pizza", "Pizza", "$12.99"},
                {"2", "Veggie Pizza", "Pizza", "$10.99"},
                {"3", "Chicken Wings", "Appetizer", "$8.99"},
                {"4", "Caesar Salad", "Salad", "$7.99"}
        };

        for (String[] product : products) {
    %>
    <tr>
        <td><%= product[0] %></td>
        <td><%= product[1] %></td>
        <td><%= product[2] %></td>
        <td><%= product[3] %></td>
        <td><a href="order.jsp?productId=<%= product[0] %>">Order Now</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="index.jsp">Back to Home</a>
</body>
</html>
