package com.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CastlingTest {

    @Test
    void testWhiteKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("g1"));
        assertTrue(t6.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t7 = t6.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getToBoard().whitePlayer().isCastled());
//        assertFalse(t7.getTransitionBoard().whitePlayer().isKingSideCastleCapable());
//        assertFalse(t7.getTransitionBoard().whitePlayer().isQueenSideCastleCapable());
    }

    @Test
    void testWhiteQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.getCoordinateAtPosition("h7"),
                        BoardUtils.getCoordinateAtPosition("h6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.getCoordinateAtPosition("h6"),
                        BoardUtils.getCoordinateAtPosition("h5")));
        assertTrue(t10.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t10.getToBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("c1"));
        assertTrue(t10.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t11 = t10.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t11.getMoveStatus().isDone());
        assertTrue(t11.getToBoard().whitePlayer().isCastled());
//        assertFalse(t11.getTransitionBoard().whitePlayer().isKingSideCastleCapable());
//        assertFalse(t11.getTransitionBoard().whitePlayer().isQueenSideCastleCapable());
    }

    @Test
    void testBlackKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("d3"),
                        BoardUtils.getCoordinateAtPosition("d4")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("f8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t7.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t7.getToBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("g8"));
        assertTrue(t7.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t8 = t7.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t8.getMoveStatus().isDone());
        assertTrue(t8.getToBoard().blackPlayer().isCastled());
//        assertFalse(t8.getTransitionBoard().blackPlayer().isKingSideCastleCapable());
//        assertFalse(t8.getTransitionBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    void testBlackQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getToBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                                BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t11.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t11.getToBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("c8"));
        assertTrue(t11.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t12 = t11.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getToBoard().blackPlayer().isCastled());
//        assertFalse(t12.getTransitionBoard().blackPlayer().isKingSideCastleCapable());
//        assertFalse(t12.getTransitionBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    void testCastleBugOne() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("f5"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPosition("c2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("a4")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getToBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                                BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t11.getMoveStatus().isDone());
    }
}
