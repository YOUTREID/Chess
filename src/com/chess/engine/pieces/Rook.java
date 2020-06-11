package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Rook extends Piece {

    private final static int[] POSSIBLE_OFFSETS = {-8, -1, 1, 8};

    public Rook(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, true);
    }

    Rook(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.rookBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int current : POSSIBLE_OFFSETS) {
            int destination = this.piecePosition;
            while (BoardUtils.isValid(destination)) {
                if (isColumnExclusion(current, destination)) {
                    break;
                }
                destination += current;
                if (BoardUtils.isValid(destination)) {
                    final Piece pieceAtDestination = board.getTile(destination).getPiece();
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, destination));
                    } else {
                        final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new MajorAttackMove(board, this, destination,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestination());
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    private static boolean isColumnExclusion(final int currentCandidate,
                                             final int candidateDestinationCoordinate) {
        return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] && (currentCandidate == -1)) ||
                (BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] && (currentCandidate == 1));
    }
}
