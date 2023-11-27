package org.vkartashov.minimax;

import org.apache.commons.lang3.SerializationUtils;

import java.util.*;

public class TicTacToeMinMaxAgent {

    private TicTacToeGame originalState;
    private TicTacToeActor agentRole;
    private boolean isMax;

    public TicTacToeMinMaxAgent(TicTacToeGame game, TicTacToeActor agentRole, boolean isMax) {
        this.originalState = game;
        this.agentRole = agentRole;
        this.isMax = isMax;
    }

    private List<TicTacToeTurnAction> resolveActions(TicTacToeGame game) {
        List<TicTacToeTurnAction> actions = new ArrayList<>();
        TicTacToeActor[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            TicTacToeActor[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                if (Objects.equals(row[j], null)) {
                    actions.add(new TicTacToeTurnAction(i, j, game.getCurrentPlayer()));
                }
            }
        }
        return actions;
    }

    public TicTacToeTurnAction minimaxDecision() {
        List<TicTacToeTurnAction> actions = resolveActions(originalState);
        Map<TicTacToeTurnAction, Double> scores = new HashMap<>();
        for (TicTacToeTurnAction action : actions) {
            TicTacToeGame gameForState = SerializationUtils.clone(originalState);
            action.apply(gameForState);
            double score = minimax(gameForState, 0);
            scores.put(action, score);
            System.out.println("Score for position " + action.getX() + ", " + action.getY() + " = " + score);
        }
        return Collections.max(scores.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    public double minimax(TicTacToeGame state, int depth) {
        double coeff = Math.pow(10, depth);
        if (state.resolveWinner() == TicTacToeActor.PLAYER_X) {
            return 1000d/ coeff;
        } else if (state.resolveWinner() == TicTacToeActor.PLAYER_O) {
            return -1000d/ coeff;
        } else if (state.isTerminal()) {
            return 0;
        }

        List<TicTacToeTurnAction> actions = resolveActions(state);
//        Collections.reverse(actions);
        double totalScore = 0;
        for (TicTacToeTurnAction action : actions) {
            TicTacToeGame gameForState = SerializationUtils.clone(state);
            action.apply(gameForState);
            double score = minimax(gameForState, depth + 1);
            totalScore += score;
        }
        return totalScore;
    }

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame(3, 3, TicTacToeActor.PLAYER_X);
        game.makeTurn(0, 0); // X
//        game.makeTurn(2, 0); // O
//        game.makeTurn(2, 1); // X
//        game.makeTurn(0, 0); // O
//        game.makeTurn(0, 0); // X
//        game.makeTurn(1, 1); // X
        game.printBoard();
        TicTacToeMinMaxAgent agent = new TicTacToeMinMaxAgent(game, TicTacToeActor.PLAYER_X, true);
        agent.minimaxDecision();
    }

}
