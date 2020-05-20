package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Cell extends JPanel {
    private final int row;
    private final int column;
    private final MinesweeperBoardData boardData;
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final Border COVERED_BORDER = BorderFactory.createRaisedBevelBorder();
    private static final Border UNCOVERED_BORDER = BorderFactory.createLoweredBevelBorder();

    public Cell(int row, int column, MinesweeperBoardData boardData) {
        this.row = row;
        this.column = column;
        this.boardData = boardData;
        initComponent();
    }

    private void initComponent() {
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setBorder(COVERED_BORDER);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void computeCellState() {
        var state = boardData.getMineState(row, column);
        if (state == MinesweeperBoardData.Element.COVERED_EMPTY || state == MinesweeperBoardData.Element.COVERED_MINE) {
            setBorder(COVERED_BORDER);
        } else {
            setBorder(UNCOVERED_BORDER);
        }
    }
}
