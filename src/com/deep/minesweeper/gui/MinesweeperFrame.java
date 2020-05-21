package com.deep.minesweeper.gui;

import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame {
    private final MinesweeperBoard board;
    private final JPanel buttonPanel;
    private final JButton startButton;
    private final JButton resetButton;
    private final JPanel infoPanel;
    private final JLabel rowsLabel;
    private final JLabel columnsLabel;
    private final JLabel minesLabel;

    private static final String ICON_PATH = "resources/minesweeper.png";

    public MinesweeperFrame(MinesweeperBoardData data) {
        super();
        this.board = new MinesweeperBoard(data);
        this.buttonPanel = new JPanel();
        this.startButton = new JButton("Start the AI");
        this.resetButton = new JButton("Reset Board");
        this.infoPanel = new JPanel();
        this.rowsLabel = new JLabel("Rows: " + data.getRows());
        this.columnsLabel = new JLabel("Columns: " + data.getColumns());
        this.minesLabel = new JLabel("Mines: " + data.getTotalMines());
        initComponents();
    }

    private void initComponents() {
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4));
        infoPanel.add(rowsLabel);
        infoPanel.add(columnsLabel);
        infoPanel.add(minesLabel);

        add(infoPanel, BorderLayout.NORTH);

        add(board, BorderLayout.CENTER);
        setResizable(false);
        setTitle("Minesweeper AI");
        setIconImage(new ImageIcon(ICON_PATH).getImage());
        pack();
    }
}
