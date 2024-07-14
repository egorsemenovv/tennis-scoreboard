<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>New match</title>
    <style><%@include file="../css/new-match-style.css"%></style>
</head>
<body>
<main>
    <form action="new-match" method="post">
        <input type="text" name="player_one_name" placeholder="Enter player name" required>
        <br/>
        <input type="text" name="player_two_name" placeholder="Enter player name" required>
        <br/>
        <button type="submit">Create</button>
    </form>
    <div align="center">
        <a class="home-link" href="home">Home</a>
    </div>
</main>
</body>
</html>