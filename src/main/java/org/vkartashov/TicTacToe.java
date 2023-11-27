package org.vkartashov;

import org.apache.commons.lang3.StringUtils;
import org.vkartashov.minimax.TicTacToeMiniMaxAgent;
import org.vkartashov.minimax.TicTacToeTurnAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TicTacToe extends JFrame {
    private final int GRID_SIZE = 3;
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private TicTacToeGame ticTacToeGame = new TicTacToeGame();
    private TicTacToeMiniMaxAgent ticTacToeMiniMaxAgent = new TicTacToeMiniMaxAgent();

    private TicTacToeActor AI_PLAYER = TicTacToeActor.PLAYER_O;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);

        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        // Initialize buttons and add them to the JFrame
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                buttons[i][j] = button;
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
                add(button);
                button.addActionListener(new ButtonListener(i, j));
            }
        }

        setVisible(true);
    }

    private void resetBoard() {
        ticTacToeGame = new TicTacToeGame();
        refreshButtons();
    }

    private void refreshButtons() {
        for (int i = 0; i < buttons.length; i++) {
            JButton[] button = buttons[i];
            for (int j = 0; j < button.length; j++) {
                JButton jButton = button[j];
                TicTacToeActor actor = ticTacToeGame.getBoard()[i][j];
                if (actor != null) {
                    jButton.setText(actor.getDisplayValue());
                } else {
                    jButton.setText(StringUtils.EMPTY);
                }
            }
        }
    }

    private class ButtonListener implements ActionListener {

        private int x;
        private int y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            if (buttonClicked.getText().isEmpty()) {
                ticTacToeGame.makeTurn(x, y);
                boolean isTerminalState = ticTacToeGame.isTerminal();
                TicTacToeActor winner = ticTacToeGame.resolveWinner();
                if (isTerminalState) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetBoard();
                } else if (winner != null) {
                    JOptionPane.showMessageDialog(null, "Player " + winner + " wins!");
                    resetBoard();
                } else {
                    refreshButtons();
                }
                if (ticTacToeGame.getCurrentPlayer() == AI_PLAYER) {
                    TicTacToeTurnAction action = ticTacToeMiniMaxAgent.minimaxDecision(ticTacToeGame);
                    buttons[action.getX()][action.getY()].doClick();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}

