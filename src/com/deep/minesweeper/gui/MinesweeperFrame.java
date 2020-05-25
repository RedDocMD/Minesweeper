package com.deep.minesweeper.gui;

import com.deep.minesweeper.ai.MinesweeperAI;
import com.deep.minesweeper.data.MinesweeperBoardData;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class MinesweeperFrame extends JFrame {
    private final MinesweeperPanel board;
    private final MinesweeperBoardData data;
    private final MinesweeperAI ai;
    private final JPanel buttonPanel;
    private final JButton aiButton;
    private final JButton playButton;
    private final JButton resetButton;
    private final JPanel infoPanel;
    private final JLabel rowsLabel;
    private final JLabel columnsLabel;
    private final JLabel minesLabel;
    private final JLabel flaggedLabel;

    private static final String ICON_PATH = "resources/minesweeper.png";

    private boolean aiPlaying;
    private boolean humanPlaying;

    public MinesweeperFrame(MinesweeperBoardData data) {
        super();
        this.board = new MinesweeperPanel(data, this);
        this.data = data;
        this.ai = new MinesweeperAI(data);
        this.buttonPanel = new JPanel();
        this.aiButton = new JButton("AI make move");
        this.resetButton = new JButton("Reset Board");
        this.playButton = new JButton("Play game");
        this.infoPanel = new JPanel();
        this.rowsLabel = new JLabel("Rows: " + data.getRows());
        this.columnsLabel = new JLabel("Columns: " + data.getColumns());
        this.minesLabel = new JLabel("Mines: " + data.getTotalMines());
        this.flaggedLabel = new JLabel("Flagged: " + data.getFlaggedCount());
        this.humanPlaying = false;
        this.aiPlaying = false;
        initComponents();
    }

    public boolean isAiPlaying() {
        return aiPlaying;
    }

    public boolean isHumanPlaying() {
        return humanPlaying;
    }

    public void updateFlagged() {
        flaggedLabel.setText("Flagged: " + data.getFlaggedCount());
    }

    private void initComponents() {
        resetButton.addActionListener(e -> {
            data.resetBoard();
            board.recomputeCellsState();
            updateFlagged();
            playButton.setEnabled(true);
            aiButton.setEnabled(true);
            aiPlaying = false;
            humanPlaying = false;
            Logger.getGlobal().info("\n" + data.toString());
        });

        playButton.addActionListener(e -> {
            humanPlaying = true;
            aiPlaying = false;
            aiButton.setEnabled(false);
            playButton.setEnabled(false);
        });

        aiButton.addActionListener(e -> {
            aiPlaying = true;
            humanPlaying = false;
            playButton.setEnabled(false);
            aiMakeMove();
        });

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(aiButton);
        buttonPanel.add(playButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4));
        infoPanel.add(rowsLabel);
        infoPanel.add(columnsLabel);
        infoPanel.add(minesLabel);
        infoPanel.add(flaggedLabel);

        add(infoPanel, BorderLayout.NORTH);

        add(board, BorderLayout.CENTER);
        setResizable(false);
        setTitle("Minesweeper AI");
        setIconImage(new ImageIcon(ICON_PATH).getImage());
        pack();
    }

    private void aiMakeMove() {
        ai.makeMove();
        board.recomputeCellsState();
        updateFlagged();
        if (data.isGameEnded()) {
            aiButton.setEnabled(false);
            announceGameEnd();
        }
    }

    public void announceGameEnd() {
        var state = data.getGameState();
        if (state == MinesweeperBoardData.GameState.WON)
            JOptionPane.showMessageDialog(this, "You have won!",
                    "Game over", JOptionPane.INFORMATION_MESSAGE);
        else if (state == MinesweeperBoardData.GameState.LOST)
            JOptionPane.showMessageDialog(this, "You have lost!",
                    "Game over", JOptionPane.INFORMATION_MESSAGE);
    }

    public void announceResetGame() {
        JOptionPane.showMessageDialog(this, "The game is over!\nPress the Reset button to start again",
                "Game over", JOptionPane.INFORMATION_MESSAGE);
    }
}
