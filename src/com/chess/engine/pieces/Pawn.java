package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {

    private static final int[] POSSIBLE_OFFSET = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(Type.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int current : POSSIBLE_OFFSET) {
            int destinationCoordinate = this.piecePosition + current;
            if (!BoardUtils.isValid((destinationCoordinate))) {
                continue;
            }
            if (current == 8 && board.getTile(destinationCoordinate).occupied()) {
                // promotions
                legalMoves.add(new MajorMove(board, this, destinationCoordinate));
            } else if (current == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindCandidateDestination = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindCandidateDestination).occupied() && !board.getTile(destinationCoordinate).occupied()) {
                    legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                } else if(current == 7 &&
                        !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                        (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                    if (board.getTile(destinationCoordinate).occupied()) {
                        final Piece pieceOnCandidate = board.getTile(destinationCoordinate).getPiece();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            // attacking into a promotion
                            legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                        }
                    }
                } else if (current == 9 &&
                        !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                         (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                    if (board.getTile(destinationCoordinate).occupied()) {
                        final Piece pieceOnCandidate = board.getTile(destinationCoordinate).getPiece();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            // attacking into a promotion
                            legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
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
