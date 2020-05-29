package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

    private final static  int[] POSSIBLE_OFFSETS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final Alliance pieceAlliance, final int piecePosition) {
        super(Type.QUEEN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int current : POSSIBLE_OFFSETS) {
            int destinationCoordinate = this.piecePosition;
            while (BoardUtils.isValid(destinationCoordinate)) {
                if (isFirstColumnExclusion(destinationCoordinate, current) ||
                        isEigthColumnExclusion(destinationCoordinate, current)) {
                    break;
                }
                destinationCoordinate += current;
                if (BoardUtils.isValid((destinationCoordinate))) {
                    final Tile candidateDestinationTile = board.getTile(destinationCoordinate);
                    if (!candidateDestinationTile.occupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, destinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                    }
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestination());
    }

    @Override
    public String toString() {
        return Type.QUEEN.toString();
    }

    private static boolean isFirstColumnExclusion(final int current, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[current] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1);
    }

    private static boolean isEigthColumnExclusion(final int current, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[current] && (candidateOffset == 9 || candidateOffset == -7 || candidateOffset == 1);
    }
}
