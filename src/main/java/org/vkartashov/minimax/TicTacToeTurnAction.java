package org.vkartashov.minimax;

import org.vkartashov.TicTacToeActor;
import org.vkartashov.TicTacToeGame;

public class TicTacToeTurnAction {

    private int x;
    private int y;

    public TicTacToeTurnAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void apply(TicTacToeGame game) {
        game.makeTurn(x, y);
    }

}
