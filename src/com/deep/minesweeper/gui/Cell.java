package com.deep.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private final int row;
    private final int column;
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        initComponent();
    }

    private void initComponent() {
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }
}
