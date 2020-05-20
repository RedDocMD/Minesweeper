package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class MinesweeperBoard extends JPanel {
    private final MinesweeperBoardData boardData;
    private final Cell[][] cells;
    private final JPanel backPanel;
    private final int rows;
    private final int columns;

    public MinesweeperBoard(MinesweeperBoardData boardData) {
        super();
        this.boardData = boardData;
        this.rows = boardData.getRows();
        this.columns = boardData.getColumns();
        this.cells = new Cell[rows][columns];
        this.backPanel = new JPanel();
        initComponent();
    }

    private void initComponent() {
        // TODO: Initialize layout and listeners
        backPanel.setLayout(new GridLayout(rows, columns));
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < columns; j++) {
                cells[i][j] = new Cell(i, j);
                var border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
                cells[i][j].setBorder(border);
                backPanel.add(cells[i][j]);
            }
        }
        add(backPanel);
    }
}
