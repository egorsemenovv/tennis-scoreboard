package org.egorsemenovv.tennisscoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.egorsemenovv.tennisscoreboard.exception.InvalidParamsException;
import org.egorsemenovv.tennisscoreboard.service.FinishedMatchesPersistenceService;

import java.io.IOException;

@WebServlet("/matches")
public class FinishedMatchesServlet extends BaseServlet {

    private static final int MATCHES_PER_PAGE = 5;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getINSTANCE();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParameter = req.getParameter("page_number");
        String playerName = req.getParameter("player_name_filter");

        int pageNumber = 1;

        try {
            if (pageParameter!=null && !pageParameter.isBlank()){
                pageNumber = Integer.parseInt(pageParameter);
                req.setAttribute("page_number", pageNumber);
            }
        } catch (NumberFormatException e) {
            throw new InvalidParamsException("page number is incorrect");
        }

        if (playerName == null) playerName = "";

        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("totalPages", finishedMatchesPersistenceService.getTotalPagesNumber(MATCHES_PER_PAGE, playerName));
        req.setAttribute("playerName", playerName);
        req.setAttribute("finishedMatches", finishedMatchesPersistenceService.getFinishedMatchesWithLimit(MATCHES_PER_PAGE, pageNumber, playerName));
        req.getRequestDispatcher("/jsp/finished-matches.jsp").forward(req, resp);
    }

}
