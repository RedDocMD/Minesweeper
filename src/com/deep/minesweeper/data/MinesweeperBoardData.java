package com.deep.minesweeper.data;

public class MinesweeperBoardData {
    private final int width;
    private final int height;
    private final int totalMines;
    private final Element[][] board;

    public MinesweeperBoardData(int width, int height, int totalMines) {
        this.width = width;
        this.height = height;
        this.totalMines = totalMines;
        this.board = new Element[height][width];
        initializeBoard();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public enum Element {
        COVERED_MINE, COVERED_EMPTY, UNCOVERED_MINE, UNCOVERED_EMPTY
    }

    private void initializeBoard() {
        //TODO: Code to initialize board with random mines
    }
}
