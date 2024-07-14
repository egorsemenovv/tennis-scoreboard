<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Error</title>
  <style><%@include file="../css/error-page-style.css" %></style>
</head>
<body>
<div class="error-container">
  <div class="error-code">${requestScope.errorCode}</div>
  <div class="error-message">${requestScope.errorMessage}</div>
  <a class="home-link" href="home">Home</a>
</div>
</body>
</html>