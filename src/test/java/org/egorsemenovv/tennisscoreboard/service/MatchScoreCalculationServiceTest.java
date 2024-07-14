package org.egorsemenovv.tennisscoreboard.service;

import org.egorsemenovv.tennisscoreboard.model.Match;

import org.egorsemenovv.tennisscoreboard.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.egorsemenovv.tennisscoreboard.model.Score.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MatchScoreCalculationServiceTest {
    private static final int PLAYER_ONE_ID = 1;
    private static final int PLAYER_TWO_ID = 2;
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getINSTANCE();
    private Match match;
    private final Player player1 = new Player(PLAYER_ONE_ID, "player1");
    private final Player player2 = new Player(PLAYER_TWO_ID, "player2");

    @BeforeEach
    void init() {
        match = Match.builder()
                .id(1)
                .player1(player1)
                .player2(player2)
                .build();
    }

    @Test
    void getAdvantageAfterFortyFortyScore() {
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(DEUS, match.getMatchScore().getPoints()[0]);
        assertEquals(DEUS, match.getMatchScore().getPoints()[1]);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertEquals(ADVANTAGE, match.getMatchScore().getPoints()[0]);
        assertEquals(DISADVANTAGE, match.getMatchScore().getPoints()[1]);
    }

    @Test
    void getGamePointAfterFortyZeroScore() {
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertEquals(FORTY, match.getMatchScore().getPoints()[0]);
        assertEquals(ZERO, match.getMatchScore().getPoints()[1]);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertEquals(ZERO, match.getMatchScore().getPoints()[0]);
        assertEquals(ZERO, match.getMatchScore().getPoints()[1]);
        assertEquals(1, match.getMatchScore().getGameScore()[0]);
        assertEquals(0, match.getMatchScore().getGameScore()[1]);
    }

    @Test
    void isTieBreakAtSixSixGamesScore() {
        for (int i = 0; i < 20; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        for (int i = 0; i < 20; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        for (int i = 0; i < 4; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        for (int i = 0; i < 4; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(6, match.getMatchScore().getGameScore()[0]);
        assertEquals(6, match.getMatchScore().getGameScore()[1]);
        assertTrue(match.getMatchScore().isTieBreak());
    }

    @Test
    void getDeusAfterFirstPlayerLosePointAtAdvantage() {
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(DEUS, match.getMatchScore().getPoints()[0]);
        assertEquals(DEUS, match.getMatchScore().getPoints()[1]);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(DISADVANTAGE, match.getMatchScore().getPoints()[0]);
        assertEquals(ADVANTAGE, match.getMatchScore().getPoints()[1]);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertEquals(DEUS, match.getMatchScore().getPoints()[0]);
        assertEquals(DEUS, match.getMatchScore().getPoints()[1]);
    }

    @Test
    void isTieBreakGame() {
        for (int i = 0; i < 20; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        for (int i = 0; i < 20; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        for (int i = 0; i < 4; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        for (int i = 0; i < 4; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        for (int i = 0; i < 3; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertEquals(3, match.getMatchScore().getTieBreakPoints()[0]);
        assertEquals(0, match.getMatchScore().getTieBreakPoints()[1]);
        for (int i = 0; i < 6; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(3, match.getMatchScore().getTieBreakPoints()[0]);
        assertEquals(6, match.getMatchScore().getTieBreakPoints()[1]);
        matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(0, match.getMatchScore().getTieBreakPoints()[0]);
        assertEquals(0, match.getMatchScore().getTieBreakPoints()[1]);
        assertEquals(0, match.getMatchScore().getTieBreakPoints()[0]);
        assertEquals(6, match.getMatchScore().getSets().getFirst().firstPlayerGamesCount());
        assertEquals(7, match.getMatchScore().getSets().getFirst().secondPlayerGamesCount());
    }

    @Test
    void isMatchEnded() {
        for (int i = 0; i < 24; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        for (int i = 0; i < 24; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_TWO_ID);
        assertEquals(6, match.getMatchScore().getSets().getFirst().firstPlayerGamesCount());
        assertEquals(0, match.getMatchScore().getSets().getFirst().secondPlayerGamesCount());
        assertEquals(0, match.getMatchScore().getSets().get(1).firstPlayerGamesCount());
        assertEquals(6, match.getMatchScore().getSets().get(1).secondPlayerGamesCount());
        for (int i = 0; i < 24; i++) matchScoreCalculationService.addPointToPlayer(match, PLAYER_ONE_ID);
        assertTrue(match.getMatchScore().isMatchEnded(3));
    }
}