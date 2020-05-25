package com.chess.engine.player;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return false;
        }
    };


    abstract boolean isDone();
}
