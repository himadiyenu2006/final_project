<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment</title>
</head>
<body>
<h1>Payment Information</h1>
<form action="processPayment" method="POST">
    <label for="orderId">Order ID:</label><br>
    <input type="text" id="orderId" name="orderId" required><br>

    <label for="amount">Amount:</label><br>
    <input type="text" id="amount" name="amount" required><br>

    <label for="paymentType">Payment Type:</label><br>
    <select id="paymentType" name="paymentType" required>
        <option value="Credit Card">Credit Card</option>
        <option value="Debit Card">Debit Card</option>
        <option value="PayPal">PayPal</option>
    </select><br><br>

    <input type="submit" value="Submit Payment">
</form>
<a href="index.jsp">Back to Home</a>
</body>
</html>
