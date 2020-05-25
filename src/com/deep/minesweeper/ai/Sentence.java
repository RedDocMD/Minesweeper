package com.deep.minesweeper.ai;

import com.deep.minesweeper.data.MinesweeperBoardData;
import com.deep.minesweeper.data.Position;

import java.util.HashSet;
import java.util.Set;

public class Sentence {
    private final Position center;
    private final Set<Position> neighbours;
    private final int mineCount;
    private final Set<Position> knownSafe;
    private final Set<Position> knownMine;

    public Sentence(Position center, Set<Position> neighbours, int mineCount, Set<Position> knownSafe, Set<Position> knownMine) {
        this.center = center;
        this.neighbours = neighbours;
        this.mineCount = mineCount;
        this.knownSafe = knownSafe;
        this.knownMine = knownMine;
    }

    public Set<Position> getKnownSafe() {
        return knownSafe;
    }

    public Set<Position> getKnownMine() {
        return knownMine;
    }

    public void doSelfInference() {
        if (mineCount == neighbours.size() - knownSafe.size()) {
            var remaining = new HashSet<>(neighbours);
            remaining.removeAll(knownSafe);
            knownMine.addAll(remaining);
        }
        if (mineCount == 0) {
            knownSafe.addAll(neighbours);
        }
    }
}
