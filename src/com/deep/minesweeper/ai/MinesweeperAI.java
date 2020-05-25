package com.deep.minesweeper.ai;

import com.deep.minesweeper.data.MinesweeperBoardData;
import com.deep.minesweeper.data.Position;

import java.util.*;
import java.util.logging.Logger;

public class MinesweeperAI {
    private final MinesweeperBoardData board;
    private final List<Sentence> sentences;
    private final Set<Position> knownMines;
    private final Set<Position> knownSafe;
    private final Set<Position> uncovered;
    private final Random generator;

    public MinesweeperAI(MinesweeperBoardData board) {
        this.board = board;
        this.sentences = new ArrayList<>();
        this.knownMines = new HashSet<>();
        this.knownSafe = new HashSet<>();
        this.generator = new Random();
        this.uncovered = board.getUncovered();
    }

    public void makeMove() {
        var move = getNextMove();
        Logger.getGlobal().info("Move: " + move);
        if (move != null) {
            board.uncoverCell(move);
            updateData();
        }
        Logger.getGlobal().info(String.format("Mine count: %d Safe count: %d", knownMines.size(), knownSafe.size()));
    }

    private void updateData() {
        var modifiedUncovered = board.getUncovered();
        knownSafe.addAll(modifiedUncovered);

        var uncoveredThisMove = new HashSet<>(modifiedUncovered);
        uncoveredThisMove.removeAll(uncovered);

        for (var move : uncoveredThisMove) {
            var neighbours = board.getNeighbours(move);
            var safeNeighbours = new HashSet<>(neighbours);
            safeNeighbours.retainAll(knownSafe);
            var mineNeighbours = new HashSet<>(neighbours);
            mineNeighbours.retainAll(knownMines);

            var newSentence = new Sentence(move, neighbours, board.getMineCount(move), safeNeighbours, mineNeighbours);
            sentences.add(newSentence);
            doInference(newSentence);
        }

        uncovered.addAll(modifiedUncovered);
    }

    private void doInference(Sentence newSentence) {
        newSentence.doSelfInference();
        knownSafe.addAll(newSentence.getKnownSafe());
        knownMines.addAll(newSentence.getKnownMine());
        flagAllMines();
    }

    private void flagAllMines() {
        for (var mine : knownMines) {
            board.flagCell(mine);
        }
    }

    private Position getNextMove() {
        if (board.isGameEnded()) return null;
        var move = getSafeMove();
        if (move != null) return move;
        else return getRandomMove();
    }

    private Position getSafeMove() {
        for (var move : knownSafe) {
            if (!uncovered.contains(move)) return move;
        }
        return null;
    }

    private Position getRandomMove() {
        var bound = board.getRows() * board.getColumns();
        var rand = generator.nextInt(bound);
        var position = new Position(rand / board.getColumns(), rand % board.getColumns());
        while (uncovered.contains(position) || knownMines.contains(position)) {
            rand = generator.nextInt(bound);
            position = new Position(rand / board.getColumns(), rand % board.getColumns());
        }
        return position;
    }
}
