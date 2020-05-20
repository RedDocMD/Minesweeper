package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Cell extends JPanel {
    private final int row;
    private final int column;
    private final MinesweeperBoardData boardData;
    private final JLabel label;

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final Border COVERED_BORDER = BorderFactory.createRaisedBevelBorder();
    private static final Border UNCOVERED_BORDER = BorderFactory.createLoweredBevelBorder();
    private static final Color[] LABEL_COLORS = {
            Color.BLACK,  // 0
            Color.BLUE,   // 1
            Color.GREEN.darker(),  // 2
            Color.RED,    // 3
            Color.CYAN,   // 4
            Color.ORANGE, // 5
            Color.YELLOW, // 6
            Color.GRAY,   // 7
            Color.MAGENTA // 8
    };

    public Cell(int row, int column, MinesweeperBoardData boardData) {
        this.row = row;
        this.column = column;
        this.boardData = boardData;
        this.label = new JLabel();
        initComponent();
    }

    private void initComponent() {
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setBorder(COVERED_BORDER);
        add(label, BorderLayout.CENTER);
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
            if (state == MinesweeperBoardData.Element.UNCOVERED_EMPTY) {
                var value = boardData.getMineCount(row, column);
                if (value != 0) {
                    label.setText(value + "");
                    label.setForeground(LABEL_COLORS[value]);
                } else {
                    label.setText("");
                }
            }
        }
    }
}
