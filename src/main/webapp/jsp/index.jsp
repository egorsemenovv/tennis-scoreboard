<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tennis Scoreboard</title>
  <style><%@include file="../css/nav-bar-style.css"%></style>
</head>
<body>
<header>
  <h3><%= "Tennis Scoreboard" %></h3>
</header>
<section>
<nav>
  <a href="home">Home</a>
  <a href="matches">Matches</a>
  <a href="new-match">New Match</a>
</nav>

<main>
  <p>Welcome to the Tennis Scoreboard. Use the navigation above to view matches or start a new match.</p>
</main>
</section>
</body>
</html>