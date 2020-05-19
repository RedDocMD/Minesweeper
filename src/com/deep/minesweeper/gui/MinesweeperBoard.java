package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import java.awt.*;

public class MinesweeperBoard extends JComponent {
    private final MinesweeperBoardData boardData;
    private final JButton[][] cells;
    private final JPanel backPanel;
    private final int rows;
    private final int columns;

    public MinesweeperBoard(MinesweeperBoardData boardData) {
        super();
        this.boardData = boardData;
        this.rows = boardData.getRows();
        this.columns = boardData.getColumns();
        this.cells = new JButton[rows][columns];
        this.backPanel = new JPanel();
        initComponent();
    }

    private void initComponent() {
        // TODO: Initialize layout and listeners
        backPanel.setLayout(new GridLayout(rows, columns));
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < columns; j++) {
                cells[i][j] = new JButton();
                backPanel.add(cells[i][j]);
            }
        }
    }
}
