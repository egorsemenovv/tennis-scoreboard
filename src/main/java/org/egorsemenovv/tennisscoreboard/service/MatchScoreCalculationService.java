package org.egorsemenovv.tennisscoreboard.service;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.dto.OngoingMatchScoreReadDTO;
import org.egorsemenovv.tennisscoreboard.exception.NoSuchResourceException;
import org.egorsemenovv.tennisscoreboard.mapper.MatchScoreReadMapper;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.egorsemenovv.tennisscoreboard.model.Player;
import org.egorsemenovv.tennisscoreboard.model.PlayerNumber;

import java.util.Optional;

import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_ONE_NUMBER;
import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_TWO_NUMBER;

public class MatchScoreCalculationService {

    @Getter
    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();
    private final MatchScoreReadMapper matchScoreReadMapper = MatchScoreReadMapper.getINSTANCE();
    private static final int NUMBER_OF_PLAYING_SETS = 3;

    private MatchScoreCalculationService(){}

    public void addPointToPlayer(Match match, int playerId) {
        PlayerNumber playerNumber;
        if (match.getPlayer1().getId() == playerId) playerNumber = PLAYER_ONE_NUMBER;
        else if (match.getPlayer2().getId() == playerId) playerNumber = PLAYER_TWO_NUMBER;
        else throw new NoSuchResourceException("Player with such id is not playing this match");
        match.getMatchScore().addPointToPlayer(playerNumber);
    }

    public boolean isMatchFinished(Match match) {
        return match.getMatchScore().isMatchEnded(NUMBER_OF_PLAYING_SETS);
    }

    public OngoingMatchScoreReadDTO getMatchScore(Match match) {
        return matchScoreReadMapper.mapFrom(match.getMatchScore());
    }

    public Optional<Player> getWinner(Match match){
        Player player = null;
        if (match.getMatchScore().getWinnerNumber() != null){
            player = match.getMatchScore().getWinnerNumber() == PLAYER_ONE_NUMBER ? match.getPlayer1() : match.getPlayer2();;
        }
        return Optional.ofNullable(player);
    }
}
