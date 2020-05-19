package com.deep.minesweeper;

import com.deep.minesweeper.data.MinesweeperBoardData;
import com.deep.minesweeper.gui.MinesweeperFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        var boardData = new MinesweeperBoardData(10, 10, 8);
        System.out.println(boardData.toString());
        EventQueue.invokeLater(() -> {
            var frame = new MinesweeperFrame(boardData);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
