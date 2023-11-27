package org.vkartashov;

public enum TicTacToeActor {

    PLAYER_X("X"),
    PLAYER_O("O");

    private String displayValue;

    private TicTacToeActor(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
