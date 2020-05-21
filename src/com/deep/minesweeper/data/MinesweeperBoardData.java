package com.deep.minesweeper.data;

import java.util.*;

public class MinesweeperBoardData {
    private final int columns;
    private final int rows;
    private final int totalMines;
    private final Element[][] board;
    private final int[][] counterBoard;
    private final Set<Position> flagged;
    
    private boolean gameEnded;
    
    private static final int MINE_VALUE = -1;

    public MinesweeperBoardData(int rows, int columns, int totalMines) {
        this.columns = columns;
        this.rows = rows;
        this.totalMines = totalMines;
        this.board = new Element[rows][columns];
        this.counterBoard = new int[rows][columns];
        this.gameEnded = false;
        this.flagged = new HashSet<>();
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
        COVERED_MINE, COVERED_EMPTY, UNCOVERED_MINE, UNCOVERED_EMPTY, FLAGGED
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

    public List<Position> getNeighbours(Position pos) {
        return getNeighbours(pos.getRow(), pos.getColumn());
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
                    if (board[neighbour.getRow()][neighbour.getColumn()] == Element.COVERED_EMPTY) {
                        counterBoard[neighbour.getRow()][neighbour.getColumn()]++;
                    }
                }
            }
        }
    }

    public void resetBoard() {
        initializeBoard();
        flagged.clear();
        gameEnded = false;
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

    public void flagCell(int row, int column) {
        if (row < 0 || column < 0 || row >= rows || column >= columns)
            throw new IllegalArgumentException("Invalid position: Position outside board");
        board[row][column] = Element.FLAGGED;
        flagged.add(new Position(row, column));
    }

    public int getFlaggedCount() {
        return flagged.size();
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void uncoverCell(int row, int column) {
        if (row < 0 || column < 0 || row >= rows || column >= columns)
            throw new IllegalArgumentException("Invalid position: Position outside board");
        if (board[row][column] == Element.COVERED_EMPTY) {
            board[row][column] = Element.UNCOVERED_EMPTY;
            recursivelyUncover(new Position(row, column), new HashSet<>());
        }
        else if (board[row][column] == Element.COVERED_MINE) {
            gameEnded = true;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (board[i][j] == Element.COVERED_MINE) board[i][j] = Element.UNCOVERED_MINE;
                }
            }
        }
    }

    private void recursivelyUncover(Position pos, Set<Position> done) {
        if (counterBoard[pos.getRow()][pos.getColumn()] == 0 && !done.contains(pos)) {
            done.add(pos);
            var neighbours = getNeighbours(pos);
            for (var neighbour : neighbours) {
                recursivelyUncover(neighbour, done);
            }
        }
        board[pos.getRow()][pos.getColumn()] = Element.UNCOVERED_EMPTY;
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
