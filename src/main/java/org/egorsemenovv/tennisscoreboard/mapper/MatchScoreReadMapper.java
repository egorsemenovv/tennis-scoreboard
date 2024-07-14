package org.egorsemenovv.tennisscoreboard.mapper;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.dto.OngoingMatchScoreReadDTO;
import org.egorsemenovv.tennisscoreboard.model.MatchScore;
import org.egorsemenovv.tennisscoreboard.model.TennisSet;

import java.util.List;

import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_ONE_NUMBER;
import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_TWO_NUMBER;

public class MatchScoreReadMapper {
    @Getter
    private static final MatchScoreReadMapper INSTANCE = new MatchScoreReadMapper();

    private MatchScoreReadMapper() {}

    public OngoingMatchScoreReadDTO mapFrom(MatchScore matchScore) {
        List<TennisSet> sets = matchScore.getSets();
        int firstPlayerNumberOfWonSets = 0;
        int secondPlayerNumberOfWonSets = 0;
        for (TennisSet set : sets) {
            if (set.firstPlayerGamesCount() >= set.secondPlayerGamesCount()) firstPlayerNumberOfWonSets++;
            else secondPlayerNumberOfWonSets++;

        }
        return new OngoingMatchScoreReadDTO(firstPlayerNumberOfWonSets,
                secondPlayerNumberOfWonSets,
                sets,
                matchScore.getGameScore()[PLAYER_ONE_NUMBER.ordinal()],
                matchScore.getGameScore()[PLAYER_TWO_NUMBER.ordinal()],
                matchScore.isTieBreak(),
                matchScore.getTieBreakPoints()[0],
                matchScore.getTieBreakPoints()[1],
                matchScore.getPoints()[PLAYER_ONE_NUMBER.ordinal()].name(),
                matchScore.getPoints()[PLAYER_TWO_NUMBER.ordinal()].name());
    }
}
