package org.vkartashov.minimax;

import org.apache.commons.lang3.SerializationUtils;
import org.vkartashov.TicTacToeActor;
import org.vkartashov.TicTacToeGame;

import java.util.*;

public class TicTacToeMiniMaxAgent {

    private List<TicTacToeTurnAction> resolveActions(TicTacToeGame game) {
        List<TicTacToeTurnAction> actions = new ArrayList<>();
        TicTacToeActor[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            TicTacToeActor[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                if (Objects.equals(row[j], null)) {
                    actions.add(new TicTacToeTurnAction(i, j));
                }
            }
        }
        return actions;
    }

    public TicTacToeTurnAction minimaxDecision(TicTacToeGame gameState) {
        List<TicTacToeTurnAction> actions = resolveActions(gameState);
        Map<TicTacToeTurnAction, Double> scores = new HashMap<>();
        for (TicTacToeTurnAction action : actions) {
            TicTacToeGame gameForState = SerializationUtils.clone(gameState);
            action.apply(gameForState);
            double score = minimax(gameForState, 0);
            scores.put(action, score);
        }
        if (gameState.getCurrentPlayer() == TicTacToeActor.PLAYER_X) {
            return Collections.max(scores.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        } else {
            return Collections.min(scores.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        }
    }

    public double minimax(TicTacToeGame state, int depth) {
        // Introduced Additional coefficient to decrease weight of far away moves that could overweight
        // winning move by opponent in the next turn
        double depthWeightCoefficient = Math.pow(10, depth);
        if (state.resolveWinner() == TicTacToeActor.PLAYER_X) {
            return 1000d/ depthWeightCoefficient;
        } else if (state.resolveWinner() == TicTacToeActor.PLAYER_O) {
            return -1000d/ depthWeightCoefficient;
        } else if (state.isTerminal()) {
            return 0;
        }

        List<TicTacToeTurnAction> actions = resolveActions(state);
        double totalScore = 0;
        for (TicTacToeTurnAction action : actions) {
            TicTacToeGame gameForState = SerializationUtils.clone(state);
            action.apply(gameForState);
            double score = minimax(gameForState, depth + 1);
            totalScore += score;
        }
        return totalScore;
    }

}
