package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {
    private final Board fromBoard;
    private final Board transitionBoard;
    private final MoveStatus moveStatus;

    MoveTransition(Board fromBoard, final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.transitionBoard = transitionBoard;
        this.moveStatus = moveStatus;

        // System.out.println(transitionBoard);
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Board getToBoard() {
        return this.transitionBoard;
    }

}
