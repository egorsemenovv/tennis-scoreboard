package org.egorsemenovv.tennisscoreboard.dto;


public record FinishedMatchReadDTO(Integer id,
                                   String playerOneName,
                                   String playerTwoName,
                                   String winner) {
}
