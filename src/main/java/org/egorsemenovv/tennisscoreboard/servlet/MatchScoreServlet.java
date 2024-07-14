package org.egorsemenovv.tennisscoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.egorsemenovv.tennisscoreboard.dto.OngoingMatchScoreReadDTO;
import org.egorsemenovv.tennisscoreboard.exception.InvalidParamsException;
import org.egorsemenovv.tennisscoreboard.exception.NoSuchResourceException;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.egorsemenovv.tennisscoreboard.model.Player;
import org.egorsemenovv.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.egorsemenovv.tennisscoreboard.service.MatchScoreCalculationService;
import org.egorsemenovv.tennisscoreboard.service.OngoingMatchesService;

import java.io.IOException;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {

    private final OngoingMatchesService onGoingMatchesService = OngoingMatchesService.getINSTANCE();
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getINSTANCE();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        if(uuid == null || uuid.isBlank()) throw new InvalidParamsException("uuid should be presented");

        Match match = onGoingMatchesService.getMatchByUuid(uuid).orElseThrow(() -> new NoSuchResourceException("Match with such UUID does not exist"));

        OngoingMatchScoreReadDTO matchScoreDTO = matchScoreCalculationService.getMatchScore(match);

        req.setAttribute("uuid", uuid);
        req.setAttribute("playerOneId", match.getPlayer1().getId());
        req.setAttribute("playerTwoId", match.getPlayer2().getId());
        req.setAttribute("matchScore", matchScoreDTO);
        req.setAttribute("playerOneName", match.getPlayer1().getName());
        req.setAttribute("playerTwoName", match.getPlayer2().getName());
        req.getRequestDispatcher("/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String playerIdParam = req.getParameter("player_id");
        int playerId;

        try {
            playerId = Integer.parseInt(playerIdParam);
        } catch (NumberFormatException e){
            throw new InvalidParamsException("player`s id is wrong");
        }

        if (uuid == null || uuid.isBlank()) throw new InvalidParamsException("UUID should be presented");

        Match match = onGoingMatchesService.getMatchByUuid(uuid).orElseThrow(() -> new NoSuchResourceException("Match with such UUID does not exist"));

        if(playerId != match.getPlayer1().getId() && playerId != match.getPlayer2().getId()) throw new NoSuchResourceException("Player with such id does not exist in this match");

        matchScoreCalculationService.addPointToPlayer(match, playerId);

        req.setAttribute("uuid", uuid);
        req.setAttribute("playerOneId", match.getPlayer1().getId());
        req.setAttribute("playerTwoId", match.getPlayer2().getId());

        if (matchScoreCalculationService.isMatchFinished(match)) {
            Player winner = matchScoreCalculationService.getWinner(match).get();
            match.setWinner(winner);
            finishedMatchesPersistenceService.save(match);
            onGoingMatchesService.removeMatchByUuid(uuid);
            OngoingMatchScoreReadDTO matchScore = matchScoreCalculationService.getMatchScore(match);
            req.setAttribute("winnerName", winner.getName());
            req.setAttribute("matchScore", matchScore);
            req.getRequestDispatcher("/jsp/final-score.jsp").forward(req, resp);
        }
        resp.sendRedirect("match-score?uuid=".concat(uuid));
    }

}
