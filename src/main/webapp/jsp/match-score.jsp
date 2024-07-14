<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match score</title>
    <style><%@include file="../css/match-score-style.css"%></style>
</head>
<body>
<div class="score-board">
    <div id="score" class="score">
        <c:if test="${requestScope.matchScore.tieBreak()==false}">
            ${requestScope.matchScore.firstPlayerCurrentScore()} : ${requestScope.matchScore.secondPlayerCurrentScore()}
        </c:if>
        <c:if test="${requestScope.matchScore.tieBreak()==true}">
            ${requestScope.matchScore.firstPlayerTieBreakScore()} : ${requestScope.matchScore.secondPlayerTieBreakScore()}
        </c:if>
    </div>
    <div class="player">${requestScope.playerOneName} : ${requestScope.playerTwoName}</div>
</div>

<div class="sets-games">
    <div class="block">
        <div class="block-title">Sets</div>
        <div class="block-content">
            ${requestScope.matchScore.firstPlayerNumberOfWonSets()} : ${requestScope.matchScore.secondPlayerNumberOfWonSets()}
        </div>
    </div>
    <div class="block">
        <div class="block-title">Games</div>
        <div class="block-content">
            <c:forEach var="game" items="${requestScope.matchScore.gamesScore()}">
                ${game.firstPlayerGamesCount()}-${game.secondPlayerGamesCount()}
            </c:forEach>
            ${requestScope.matchScore.firstPlayerLastGameScore()}-${requestScope.matchScore.secondPlayerLastGameScore()}
        </div>
    </div>
</div>

<div class="buttons">
    <form action="match-score" method="post" style="display:inline;">
        <input type="hidden" name="uuid" value="${requestScope.uuid}">
        <input type="hidden" name="player_id" value="${requestScope.playerOneId}">
        <button class="btn" type="submit">Add point to 1st</button>
    </form>
    <form action="match-score" method="post" style="display:inline;">
        <input type="hidden" name="uuid" value="${requestScope.uuid}">
        <input type="hidden" name="player_id" value="${requestScope.playerTwoId}">
        <button class="btn" type="submit">Add point to 2nd</button>
    </form>
</div>
</body>
</html>