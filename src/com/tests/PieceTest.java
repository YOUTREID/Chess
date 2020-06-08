package com.tests;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.player.MoveTransition;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {


    @Test
    public void testMiddleQueenOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 36));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 31);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testLegalMoveAllAvailable() {

        final Board.Builder boardBuilder = new Board.Builder();
        // Black Layout
        boardBuilder.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 28));
        // White Layout
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 36));
        boardBuilder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        boardBuilder.setMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 13);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f6"));
        final Move wm3 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c5"));
        final Move wm4 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g5"));
        final Move wm5 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c3"));
        final Move wm6 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g3"));
        final Move wm7 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d2"));
        final Move wm8 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        assertTrue(whiteLegals.contains(wm3));
        assertTrue(whiteLegals.contains(wm5));
        assertTrue(whiteLegals.contains(wm6));
        assertTrue(whiteLegals.contains(wm7));
        assertTrue(whiteLegals.contains(wm8));

        final Board.Builder boardBuilder2 = new Board.Builder();
        // Black Layout
        boardBuilder2.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder2.setPiece(new Knight(Alliance.BLACK, 28));
        // White Layout
        boardBuilder2.setPiece(new Knight(Alliance.WHITE, 36));
        boardBuilder2.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        boardBuilder2.setMoveMaker(Alliance.BLACK);
        final Board board2 = boardBuilder2.build();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();

        final Move bm1 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move bm2 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move bm3 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c6"));
        final Move bm4 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g6"));
        final Move bm5 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c4"));
        final Move bm6 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g4"));
        final Move bm7 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d3"));
        final Move bm8 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f3"));

        assertEquals(blackLegals.size(), 13);

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));
        assertTrue(blackLegals.contains(bm3));
        assertTrue(blackLegals.contains(bm5));
        assertTrue(blackLegals.contains(bm6));
        assertTrue(blackLegals.contains(bm7));
        assertTrue(blackLegals.contains(bm8));
    }

    @Test
    public void testKnightInCorners() {
        final Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 0));
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 56));
        boardBuilder.setPiece(new King(Alliance.WHITE, 60, false, false));
        boardBuilder.setMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(blackLegals.size(), 7);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("b3"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("c2"));
        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        final Move bm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("b6"));
        final Move bm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("c7"));
        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }

    @Test
    public void testMiddleBishopOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 35));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("a7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("b6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("c5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("f2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("g1"))));
    }

    @Test
    public void testTopLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 0));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(board.getTile(0).getPiece(), board.getTile(0).getPiece());
        assertNotNull(board.getTile(0).getPiece());
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testTopRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 7));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("a1"))));
    }

    @Test
    public void testBottomLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("h8"))));
    }

    @Test
    public void testBottomRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 63));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("a8"))));
    }

    @Test
    public void testMiddleRookOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 36));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testPawnPromotion() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 22, false, false));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 15));
        builder.setPiece(new King(Alliance.WHITE, 52, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "h7"), BoardUtils.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
    }

    @Test
    public void testSimpleWhiteEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e2"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("e8"), BoardUtils.getCoordinateAtPosition("d8"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d6"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
    }

    @Test
    public void testSimpleBlackEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e1"), BoardUtils.getCoordinateAtPosition("d1"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("d1"), BoardUtils.getCoordinateAtPosition("c1"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d4"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
        assertTrue(t5.getMoveStatus().isDone());
        final Move m6 = Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("e3"));
        final MoveTransition t6 = t5.getToBoard().currentPlayer().makeMove(m6);
    }

    @Test
    public void testEnPassant2() {
        final Board board = Board.createStandardBoard();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e2"), BoardUtils.getCoordinateAtPosition("e3"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPosition("h7"), BoardUtils.getCoordinateAtPosition("h5"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPosition("e3"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPosition("h5"), BoardUtils.getCoordinateAtPosition("h4"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPosition("g2"), BoardUtils.getCoordinateAtPosition("g4"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
        assertTrue(t5.getMoveStatus().isDone());
    }

    @Test
    public void testKingEquality() {
        final Board board = Board.createStandardBoard();
        final Board board2 = Board.createStandardBoard();
        assertEquals(board.getTile(60).getPiece(), board2.getTile(60).getPiece());
    }

}
