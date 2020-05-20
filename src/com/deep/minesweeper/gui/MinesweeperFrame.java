package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame {
    private final MinesweeperBoard board;

    public MinesweeperFrame(MinesweeperBoardData data) {
        super();
        this.board = new MinesweeperBoard(data);
        initComponents();
    }

    private void initComponents() {
        add(this.board, BorderLayout.CENTER);
        setResizable(false);
        setTitle("Minesweeper AI");
        pack();
    }
}
