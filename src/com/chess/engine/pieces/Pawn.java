package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private static final int[] POSSIBLE_OFFSET = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(Type.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int current : POSSIBLE_OFFSET) {
            final int destination = this.piecePosition + (this.getPieceAlliance().getDirection() * current);

            if (!BoardUtils.isValid(destination)) {
                continue;
            }

            if (current == 8 && !board.getTile(destination).occupied()) {
                legalMoves.add(new Move.MajorMove(board, this, destination));
            } else if (current == 16 && this.isFirstMove() &&
                    (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindDestination = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindDestination).occupied() &&
                    !board.getTile(destination).occupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, destination));
                }
            } else if (current == 7 &&
                     !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                     (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(destination).occupied()) {
                    final Piece piece  = board.getTile(destination).getPiece();
                    if (this.pieceAlliance != piece.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, destination));
                    }
                }
            } else if (current == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(destination).occupied()) {
                    final Piece piece = board.getTile(destination).getPiece();
                    if (this.pieceAlliance != piece.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, destination));
                    }
                }
            }
        }
        return ImmutableList.copyOf((legalMoves));
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestination());
    }

    @Override
    public String toString() {
        return Type.PAWN.toString();
    }
}
