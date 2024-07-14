<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Matches</title>
  <style><%@include file="../css/finished-matches-style.css" %></style>
</head>
<body>
<div>
  <form action="matches" method="get">
    <input type="text" name="player_name_filter" placeholder="Enter player name" value="${requestScope.playerName}">
    <button type="submit">Search</button>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Player One</th>
        <th>Player Two</th>
        <th>Winner</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="match" items="${requestScope.finishedMatches}">
        <tr>
          <td>${match.id()}</td>
          <td>${match.playerOneName()}</td>
          <td>${match.playerTwoName()}</td>
          <td>${match.winner()}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div class="nav-buttons">
      <button type="submit" name="page_number" value="${requestScope.pageNumber - 1}" ${requestScope.pageNumber == 1 ? 'disabled' : ''}>Previous</button>
      <span class="page-number">Page ${requestScope.pageNumber}</span>
      <button type="submit" name="page_number" value="${requestScope.pageNumber + 1}" ${requestScope.pageNumber == requestScope.totalPages ? 'disabled' : ''}>Next</button>
    </div>
  </form>
  <div align="center">
    <a class="home-link" href="home" >Home</a>
  </div>
</div>
</body>
</html>