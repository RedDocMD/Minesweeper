package com.deep.minesweeper.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperBoardData {
    private final int columns;
    private final int rows;
    private final int totalMines;
    private final Element[][] board;
    private final int[][] counterBoard;
    private static final int MINE_VALUE = -1;

    public MinesweeperBoardData(int rows, int columns, int totalMines) {
        this.columns = columns;
        this.rows = rows;
        this.totalMines = totalMines;
        this.board = new Element[rows][columns];
        this.counterBoard = new int[rows][columns];
        initializeBoard();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public enum Element {
        COVERED_MINE, COVERED_EMPTY, UNCOVERED_MINE, UNCOVERED_EMPTY
    }

    public List<Position> getNeighbours(int row, int column) {
        var neighbours = new ArrayList<Position>();
        final int[] rowAdjustments = {-1, 0, 1};
        final int[] columnAdjustments = {-1, 0 , 1};
        for (var rowAdj : rowAdjustments) {
            for (var columnAdj : columnAdjustments) {
                if (rowAdj == 0 && columnAdj == 0) continue;
                var newRow = row + rowAdj;
                var newColumn = column + columnAdj;
                if (newRow < 0 || newRow >= rows || newColumn < 0 || newColumn >= columns) continue;
                neighbours.add(new Position(newRow, newColumn));
            }
        }
        return neighbours;
    }

    private void initializeBoard() {
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < columns; j++) {
                board[i][j] = Element.COVERED_EMPTY;
                counterBoard[i][j] = 0;
            }
        }
        final int maxCount = rows * columns;
        var generator = new Random();
        for (var i = 0; i < totalMines;) {
            var mineIndex = generator.nextInt(maxCount);
            var mineRow = mineIndex / columns;
            var mineColumn = mineIndex % columns;
            if (board[mineRow][mineColumn] == Element.COVERED_EMPTY) {
                board[mineRow][mineColumn] = Element.COVERED_MINE;
                ++i;
                var neighbours = getNeighbours(mineRow, mineColumn);
                counterBoard[mineRow][mineColumn] = MINE_VALUE;
                for (var neighbour : neighbours) {
                    counterBoard[neighbour.getRow()][neighbour.getColumn()]++;
                }
            }
        }
    }

    public int getMineCount(int row, int column) {
        if (row < 0 || column < 0 || row >= rows || column >= columns)
            throw new IllegalArgumentException("Invalid position: Position outside board");
        return counterBoard[row][column];
    }

    public Element getMineState(int row, int column) {
        if (row < 0 || column < 0 || row >= rows || column >= columns)
            throw new IllegalArgumentException("Invalid position: Position outside board");
        return board[row][column];
    }

    public void uncoverCell(int row, int column) {
        if (row < 0 || column < 0 || row >= rows || column >= columns)
            throw new IllegalArgumentException("Invalid position: Position outside board");
        if (board[row][column] == Element.COVERED_EMPTY) board[row][column] = Element.UNCOVERED_EMPTY;
        else if (board[row][column] == Element.COVERED_MINE) board[row][column] = Element.UNCOVERED_MINE;
    }

    @Override
    public String toString() {
        var out = new StringBuilder();
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < columns; j++) {
                out.append(counterBoard[i][j]).append(" ");
            }
            out.append('\n');
        }
        return out.toString();
    }
}
