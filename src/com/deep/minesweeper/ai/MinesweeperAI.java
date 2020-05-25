package com.deep.minesweeper.ai;

import com.deep.minesweeper.data.MinesweeperBoardData;
import com.deep.minesweeper.data.Position;

import java.util.*;
import java.util.logging.Logger;

public class MinesweeperAI {
    private final MinesweeperBoardData board;
    private final List<Position> sentences;
    private final Set<Position> knownMines;
    private final Set<Position> knownSafe;
    private final Random generator;

    public MinesweeperAI(MinesweeperBoardData board) {
        this.board = board;
        this.sentences = new ArrayList<>();
        this.knownMines = new HashSet<>();
        this.knownSafe = new HashSet<>();
        this.generator = new Random();
    }

    public void makeNextMove() {
        var move = getNextMove();
        Logger.getGlobal().info("Move: " + move);
        if (move != null) {
            board.uncoverCell(move);
        }
    }

    private Position getNextMove() {
        if (board.isGameEnded()) return null;
        var move = getSafeMove();
        if (move != null) return move;
        else return getRandomMove();
    }

    private Position getSafeMove() {
        var uncovered = board.getUncovered();
        for (var move : knownSafe) {
            if (!uncovered.contains(move)) return move;
        }
        return null;
    }

    private Position getRandomMove() {
        var uncovered = board.getUncovered();
        var bound = board.getRows() * board.getColumns();
        var rand = generator.nextInt(bound);
        var position = new Position(rand / board.getColumns(), rand % board.getColumns());
        while (uncovered.contains(position) && knownMines.contains(position)) {
            rand = generator.nextInt(bound);
            position = new Position(rand / board.getColumns(), rand % board.getColumns());
        }
        return position;
    }
}
