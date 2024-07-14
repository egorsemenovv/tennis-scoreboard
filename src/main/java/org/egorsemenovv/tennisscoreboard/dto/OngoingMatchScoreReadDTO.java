package org.egorsemenovv.tennisscoreboard.dto;

import org.egorsemenovv.tennisscoreboard.model.TennisSet;

import java.util.List;

public record OngoingMatchScoreReadDTO(int firstPlayerNumberOfWonSets,
                                       int secondPlayerNumberOfWonSets,
                                       List<TennisSet> gamesScore,
                                       int firstPlayerLastGameScore,
                                       int secondPlayerLastGameScore,
                                       boolean tieBreak,
                                       int firstPlayerTieBreakScore,
                                       int secondPlayerTieBreakScore,
                                       String firstPlayerCurrentScore,
                                       String secondPlayerCurrentScore){
}
