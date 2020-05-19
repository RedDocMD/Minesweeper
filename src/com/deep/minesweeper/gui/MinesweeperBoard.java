package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;

public class MinesweeperBoard extends JComponent {
    private final MinesweeperBoardData boardData;
    private final JButton[][] cells;

    public MinesweeperBoard(MinesweeperBoardData boardData) {
        super();
        this.boardData = boardData;
        this.cells = new JButton[boardData.getRows()][boardData.getColumns()];
        initComponent();
    }

    private void initComponent() {
        // TODO: Initialize layout and listeners
    }
}
