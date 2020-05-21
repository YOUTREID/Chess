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

public class Rook extends Piece {

    private final static  int[] POSSIBLE_OFFSETS = {-8, -1, 1, 8};

    Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : POSSIBLE_OFFSETS) {
            int destination = this.piecePosition;
            while (BoardUtils.isValid(destination)) {
                if (isFirstColumnExclusion(destination, candidateOffset) ||
                        isEigthColumnExclusion(destination, candidateOffset)) {
                    break;
                }
                destination += candidateOffset;
                if (BoardUtils.isValid((destination))) {
                    final Tile candidateDestinationTile = board.getTile(destination);
                    if (!candidateDestinationTile.occupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, destination));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, destination, pieceAtDestination));
                        }
                    }
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int current, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[current] && (candidateOffset == -1);
    }

    private static boolean isEigthColumnExclusion(final int current, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[current] && (candidateOffset == 1);
    }
}
