<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Pizza</title>
</head>
<body>
<h1>Order Your Pizza</h1>

<form action="submitOrder" method="post">
    <label for="customerName">Name:</label><br>
    <input type="text" id="customerName" name="customerName" required><br><br>

    <label for="contact">Contact:</label><br>
    <input type="text" id="contact" name="contact" required><br><br>

    <label for="address">Delivery Address:</label><br>
    <input type="text" id="address" name="address" required><br><br>

    <label for="product">Select Pizza:</label><br>
    <select id="product" name="product">
        <option value="Margherita">Margherita</option>
        <option value="Pepperoni">Pepperoni</option>
        <option value="Veggie">Veggie</option>
        <option value="BBQ Chicken">BBQ Chicken</option>
    </select><br><br>

    <label for="quantity">Quantity:</label><br>
    <input type="number" id="quantity" name="quantity" min="1" value="1" required><br><br>

    <input type="submit" value="Place Order">
</form>

<br>
<a href="index.jsp">Back to Home</a>
</body>
</html>
