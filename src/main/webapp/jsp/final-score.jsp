<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Winner: ${requestScope.winnerName}</title>
    <style><%@include file="../css/match-score-style.css" %></style>
</head>
<body>
<div id="score" class="score">
    ${requestScope.winnerName}🏆
</div>
<div class="sets-games">
    <div class="block">
        <div class="block-title">Sets</div>
        <div class="block-content">
            ${requestScope.matchScore.firstPlayerNumberOfWonSets()}
            : ${requestScope.matchScore.secondPlayerNumberOfWonSets()}
        </div>
    </div>
    <div class="block">
        <div class="block-title">Games</div>
        <div class="block-content">
            <c:forEach var="game" items="${requestScope.matchScore.gamesScore()}">
                ${game.firstPlayerGamesCount()}-${game.secondPlayerGamesCount()}
            </c:forEach>
        </div>
    </div>
    <a class="home-link" href="home">Home</a>
</div>
</body>
</html>
