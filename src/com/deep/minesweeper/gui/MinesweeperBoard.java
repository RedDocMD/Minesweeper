package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.im.spi.InputMethod;

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
                cells[i][j] = new Cell(i, j, boardData);
                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var cell = (Cell) e.getComponent();
                        System.out.println("Hello");
                        System.out.println(e.getModifiersEx());
                        System.out.println(InputEvent.getModifiersExText(e.getModifiersEx()));
                        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                            // Left click
                            System.out.println("Hi");
                            boardData.uncoverCell(cell.getRow(), cell.getColumn());
                            cell.computeCellState();
                            System.out.println(cell.getBorder());
                        }
                    }
                });
                backPanel.add(cells[i][j]);
            }
        }
        add(backPanel);
    }
}
