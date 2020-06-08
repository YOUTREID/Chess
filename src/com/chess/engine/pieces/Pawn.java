package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {

    private static final int[] POSSIBLE_OFFSET = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(Type.PAWN, piecePosition, pieceAlliance, true);
    }

    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(Type.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
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
                if (this.pieceAlliance.isPawnPromotionSquare(destination)) {
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, destination)));
                } else {
                    legalMoves.add(new PawnMove(board, this, destination));
                }
            } else if (current == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
                final int behindDestination = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindDestination).occupied() &&
                    !board.getTile(destination).occupied()) {
                    legalMoves.add(new PawnJump(board, this, destination));
                }
            } else if (current == 7 &&
                     !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                     (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(destination).occupied()) {
                    final Piece piece  = board.getTile(destination).getPiece();
                    if (this.pieceAlliance != piece.getPieceAlliance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(destination)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, destination, piece)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, destination, piece));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, destination, pieceOnCandidate));
                        }
                    }
                }
            } else if (current == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(destination).occupied()) {
                    final Piece piece = board.getTile(destination).getPiece();
                    if (this.pieceAlliance != piece.getPieceAlliance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(destination)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, destination, piece)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, destination, piece));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, destination, pieceOnCandidate));
                        }
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

    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }
}
