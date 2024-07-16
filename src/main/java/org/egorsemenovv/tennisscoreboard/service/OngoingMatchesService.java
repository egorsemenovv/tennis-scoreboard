package org.egorsemenovv.tennisscoreboard.service;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.model.Match;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    @Getter
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<String, Match> onGoingMatches = new ConcurrentHashMap<>();

    private OngoingMatchesService(){}

    public String addMatch(Match match){
        String uuid = UUID.randomUUID().toString();
        while (onGoingMatches.containsKey(uuid)) uuid = UUID.randomUUID().toString();
        onGoingMatches.put(uuid, match);
        return uuid;
    }

    public Optional<Match> getMatchByUuid(String uuid){
        return Optional.ofNullable(onGoingMatches.get(uuid));
    }

    public void removeMatchByUuid(String uuid){
        onGoingMatches.remove(uuid);
    }
}
