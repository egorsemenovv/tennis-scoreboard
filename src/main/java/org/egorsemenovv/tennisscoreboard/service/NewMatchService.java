package org.egorsemenovv.tennisscoreboard.service;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.dto.MatchCreateDTO;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.egorsemenovv.tennisscoreboard.model.Player;
import org.egorsemenovv.tennisscoreboard.repository.PlayerRepository;
import java.util.Optional;

public class NewMatchService {

    @Getter
    private static final NewMatchService INSTANCE = new NewMatchService();
    private final PlayerRepository  playerRepository = PlayerRepository.getINSTANCE();

    public Match createNewMatch(MatchCreateDTO matchCreateDTO){
        Optional<Player> player1 = playerRepository.findByName(matchCreateDTO.playerOneName());
        Optional<Player> player2 = playerRepository.findByName(matchCreateDTO.playerTwoName());

        Player playerOne;
        Player playerTwo;

        playerOne = player1.orElseGet(() -> playerRepository.save(Player.builder().name(matchCreateDTO.playerOneName()).build()));
        playerTwo = player2.orElseGet(() -> playerRepository.save(Player.builder().name(matchCreateDTO.playerTwoName()).build()));

        return Match.builder()
                .player1(playerOne)
                .player2(playerTwo)
                .build();
    }

}
