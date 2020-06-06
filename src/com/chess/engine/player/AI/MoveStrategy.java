package com.chess.engine.player.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.gui.Table;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board, int depth);

}
