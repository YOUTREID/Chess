package com.chess.engine.player.AI;

import com.chess.engine.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
