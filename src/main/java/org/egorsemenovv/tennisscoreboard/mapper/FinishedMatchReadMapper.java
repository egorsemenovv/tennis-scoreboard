package org.egorsemenovv.tennisscoreboard.mapper;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.dto.FinishedMatchReadDTO;
import org.egorsemenovv.tennisscoreboard.model.Match;

public class FinishedMatchReadMapper {

    @Getter
    private static final FinishedMatchReadMapper INSTANCE = new FinishedMatchReadMapper();

    private FinishedMatchReadMapper() {
    }

    public FinishedMatchReadDTO mapFrom(Match match) {
        return new FinishedMatchReadDTO(match.getId(),
                match.getPlayer1().getName(),
                match.getPlayer2().getName(),
                match.getWinner().getName());
    }
}
