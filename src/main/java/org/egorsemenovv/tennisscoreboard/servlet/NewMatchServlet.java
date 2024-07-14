package org.egorsemenovv.tennisscoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.egorsemenovv.tennisscoreboard.dto.MatchCreateDTO;
import org.egorsemenovv.tennisscoreboard.exception.InvalidParamsException;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.egorsemenovv.tennisscoreboard.service.NewMatchService;
import org.egorsemenovv.tennisscoreboard.service.OngoingMatchesService;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {

    private final NewMatchService newMatchService = NewMatchService.getINSTANCE();
    private final OngoingMatchesService onGoingMatchesService = OngoingMatchesService.getINSTANCE();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/new-match.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String playerOneName = req.getParameter("player_one_name");
        String playerTwoName = req.getParameter("player_two_name");

       if (playerOneName == null || playerTwoName == null || playerOneName.isBlank() || playerTwoName.isBlank() || playerOneName.equals(playerTwoName)) {
           throw new InvalidParamsException("Players names should not be blank or the same");
       }

        Match newMatch = newMatchService.createNewMatch(new MatchCreateDTO(playerOneName, playerTwoName));
        String uuid = onGoingMatchesService.addMatch(newMatch);
        resp.sendRedirect("match-score?uuid=".concat(uuid));
    }
}
