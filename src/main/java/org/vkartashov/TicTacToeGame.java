package org.vkartashov.minimax;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Objects;

public class TicTacToeGame implements Serializable {

    private TicTacToeActor[][] board;
    private TicTacToeActor currentPlayer;

    private int winCondition;

    public TicTacToeGame(int boardSize, int winCondition, TicTacToeActor startingPlayer) {
        this.board = new TicTacToeActor[boardSize][boardSize];
        this.winCondition = winCondition;
        this.currentPlayer = startingPlayer;
    }

    public TicTacToeActor[][] getBoard() {
        return SerializationUtils.clone(board);
    }

    public TicTacToeActor getCurrentPlayer() {
        return currentPlayer;
    }

    public void makeTurn(int i, int j) {
        board[i][j] = currentPlayer;
        currentPlayer = currentPlayer == TicTacToeActor.PLAYER_X ? TicTacToeActor.PLAYER_O : TicTacToeActor.PLAYER_X;
    }

    public TicTacToeActor resolveWinner() {
        // Check if the input board is a valid 3x3 grid
        if (board == null || board.length != 3 || board[0].length != 3) {
            throw new IllegalArgumentException("Invalid board configuration");
        }

        // Check all rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != null) {
                return board[i][0];
            }

            // Check columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != null) {
                return board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != null) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != null) {
            return board[0][2];
        }

        // No winner found
        return null;
    }


    private boolean checkRowCol(TicTacToeActor c1, TicTacToeActor c2, TicTacToeActor c3) {
        return Objects.equals(c1, c2) && Objects.equals(c2, c3) && !Objects.equals(c1, null);
    }

    public boolean isTerminal() {
        for (TicTacToeActor[] ticTacToeActors : board) {
            for (TicTacToeActor ticTacToeActor : ticTacToeActors) {
                if (Objects.equals(ticTacToeActor, null)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (TicTacToeActor[] ticTacToeActors : board) {
            for (TicTacToeActor ticTacToeActor : ticTacToeActors) {
                System.out.print(ticTacToeActor == null ? "_ " : ticTacToeActor.getDisplayValue() + " ");
            }
            System.out.println();
        }
    }

}
