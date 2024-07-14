package org.egorsemenovv.tennisscoreboard.service;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.dto.FinishedMatchReadDTO;
import org.egorsemenovv.tennisscoreboard.mapper.FinishedMatchReadMapper;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.egorsemenovv.tennisscoreboard.repository.MatchRepository;
import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesPersistenceService {

    @Getter
    private static final FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();
    private final MatchRepository matchRepository = MatchRepository.getINSTANCE();
    private final FinishedMatchReadMapper finishedMatchReadMapper = FinishedMatchReadMapper.getINSTANCE();

    private FinishedMatchesPersistenceService(){}

    public void save(Match match){
        matchRepository.save(match);
    }

    public List<FinishedMatchReadDTO> getFinishedMatchesWithLimit(int matchesPerPage, int pageNumber, String playerName){
        List<FinishedMatchReadDTO> allMatchesDTO = new ArrayList<>();
        int offset = (pageNumber - 1) * matchesPerPage;
        List<Match> foundMatches = matchRepository.findMatchesWithLimitAndOffsetByName(matchesPerPage, offset, playerName);
        for (Match match : foundMatches) {
            allMatchesDTO.add(finishedMatchReadMapper.mapFrom(match));
        }
        return allMatchesDTO;
    }

    public int getTotalPagesNumber(int matchesPerPage, String playerName){
        long totalMatchesNumber = matchRepository.findAllMatchesCountByPlayerName(playerName);
        int totalPagesNumber = (int) (totalMatchesNumber/ matchesPerPage);
        if ((totalMatchesNumber - (long) totalPagesNumber * matchesPerPage >= 1) || totalPagesNumber == 0) totalPagesNumber++;
        return totalPagesNumber;
    }
}
