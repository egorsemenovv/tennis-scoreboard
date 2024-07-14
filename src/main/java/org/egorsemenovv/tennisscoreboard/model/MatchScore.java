package org.egorsemenovv.tennisscoreboard.model;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_ONE_NUMBER;
import static org.egorsemenovv.tennisscoreboard.model.PlayerNumber.PLAYER_TWO_NUMBER;
import static org.egorsemenovv.tennisscoreboard.model.Score.*;

@Getter
public class MatchScore {

    private final Score[] points = new Score[]{ZERO, ZERO};
    private final int[] gameScore = new int[]{0, 0};
    private final List<TennisSet> sets = new ArrayList<>();
    private boolean tieBreak = false;
    private final int[] tieBreakPoints = new int[]{0, 0};
    private PlayerNumber winnerNumber = null;

    public void addPointToPlayer(PlayerNumber playerNumber){
        if (winnerNumber != null) return;
        if(!tieBreak) addScoreToPlayerInRegularMode(playerNumber.ordinal());
        else addScoreToPlayerInTieBreakMode(playerNumber.ordinal());
        if(checkIfGameEnded()){
            if (tieBreak){
                if (tieBreakPoints[0] == 7) gameScore[0]++;
                else gameScore[1]++;
            }
            tieBreak = false;
            tieBreakPoints[0] = tieBreakPoints[1] = 0;
            sets.add(new TennisSet(gameScore[0], gameScore[1]));
            gameScore[0] = gameScore[1] = 0;
        }
    }


    private void addScoreToPlayerInTieBreakMode(int playerNumber) {
        tieBreakPoints[playerNumber]++;
    }

    private boolean checkIfGameEnded() {
        if (tieBreak) return tieBreakPoints[0] == 7 || tieBreakPoints[1] == 7;
        if (gameScore[0] == 6 && gameScore[1]== 6){
            tieBreak = true;
            return false;
        }
        return Math.abs(gameScore[1] - gameScore[0]) >= 2 && (gameScore[0] >= 6 || gameScore[1] >= 6);
    }

    private void addScoreToPlayerInRegularMode(int playerNumber) {
        switch (points[playerNumber]){
            case ZERO -> points[playerNumber] = FIFTEEN;
            case FIFTEEN -> points[playerNumber] = THIRTY;
            case THIRTY -> {
                if (points[1 - playerNumber] == FORTY){
                    points[playerNumber] = DEUS;
                    points[1 - playerNumber] = DEUS;
                }
                else {
                    points[playerNumber] = FORTY;
                }
            }
            case DEUS -> {
                points[playerNumber] = ADVANTAGE;
                points[1-playerNumber] = DISADVANTAGE;
            }
            case FORTY, ADVANTAGE -> {
                gameScore[playerNumber]++;
                points[0]=ZERO;
                points[1]=ZERO;
            }
            case DISADVANTAGE -> {
                points[playerNumber] = DEUS;
                points[1 - playerNumber] = DEUS;
            }
        }
    }

    public boolean isMatchEnded(int numberOfPlayingSets){
        if (winnerNumber != null) return true;
        int firstPlayerSetsCount = 0;
        int secondPlayerSetsCount = 0;
        for(TennisSet set : sets){
            if (set.firstPlayerGamesCount() >= set.secondPlayerGamesCount()) firstPlayerSetsCount++;
            else secondPlayerSetsCount++;
        }
        if (firstPlayerSetsCount == numberOfPlayingSets / 2 + 1) {
            winnerNumber = PLAYER_ONE_NUMBER;
            return true;
        }
        if (secondPlayerSetsCount == numberOfPlayingSets /2 + 1){
            winnerNumber = PLAYER_TWO_NUMBER;
            return true;
        }
        return false;
    }

}
