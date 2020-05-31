package com.chess;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.gui.Table;

import java.util.Arrays;

public class JChess {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();

    }
}
