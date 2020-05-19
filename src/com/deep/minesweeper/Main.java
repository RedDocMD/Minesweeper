package com.deep.minesweeper;

import com.deep.minesweeper.data.MinesweeperBoardData;

public class Main {
    public static void main(String[] args) {
        var boardData = new MinesweeperBoardData(10, 10, 8);
        System.out.println(boardData.toString());
    }
}
