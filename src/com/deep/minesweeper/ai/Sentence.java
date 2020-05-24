package com.deep.minesweeper.ai;

import com.deep.minesweeper.data.MinesweeperBoardData;
import com.deep.minesweeper.data.Position;

import java.util.Set;

public class Sentence {
    private final Position center;
    private final Set<Position> neighbours;
    private final MinesweeperBoardData board;

    public Sentence(Position center, MinesweeperBoardData board) {
        this.center = center;
        this.board = board;
        neighbours = board.getNeighbours(center);
    }
}
