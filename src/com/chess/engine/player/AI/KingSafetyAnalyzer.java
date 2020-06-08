package com.chess.engine.player.AI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public final class KingSafetyAnalyzer {

    private static final KingSafetyAnalyzer INSTANCE = new KingSafetyAnalyzer();
    private static final List<boolean[]> COLUMNS = initColumns();

    private KingSafetyAnalyzer() {
    }

    public static KingSafetyAnalyzer get() {
        return INSTANCE;
    }

    private static List<boolean[]> initColumns() {
        final List<boolean[]> columns = new ArrayList<>();
        columns.add(BoardUtils.FIRST_COLUMN);
        columns.add(BoardUtils.SECOND_COLUMN);
        columns.add(BoardUtils.THIRD_COLUMN);
        columns.add(BoardUtils.FOURTH_COLUMN);
        columns.add(BoardUtils.FIFTH_COLUMN);
        columns.add(BoardUtils.SIXTH_COLUMN);
        columns.add(BoardUtils.SEVENTH_COLUMN);
        columns.add(BoardUtils.EIGHTH_COLUMN);
        return columns;
    }

    public KingDistance calculateKingTropism(final Player player) {
        final int playerKingSquare = player.getPlayerKing().getPiecePosition();
        final Collection<Move> enemyMoves = player.getOpponent().getLegalMoves();
        Piece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for(final Move move : enemyMoves) {
            final int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestination());
            if(currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getMovedPiece();
            }
        }
        return new KingDistance(closestPiece, closestDistance);
    }

    private int calculateChebyshevDistance(final int pieceOneSquare,
                                           final int pieceTwoSquare) {

        final int squareOneRank = getRank(pieceOneSquare);
        final int squareTwoRank = getRank(pieceTwoSquare);

        final int squareOneFile = getFile(pieceOneSquare);
        final int squareTwoFile = getFile(pieceTwoSquare);

        final int rankDistance = Math.abs(squareTwoRank - squareOneRank);
        final int fileDistance = Math.abs(squareTwoFile - squareOneFile);

        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(final int coordinate) {
        if(BoardUtils.FIRST_COLUMN[coordinate]) {
            return 1;
        } else if(BoardUtils.SECOND_COLUMN[coordinate]) {
            return 2;
        } else if(BoardUtils.THIRD_COLUMN[coordinate]) {
            return 3;
        } else if(BoardUtils.FOURTH_COLUMN[coordinate]) {
            return 4;
        } else if(BoardUtils.FIFTH_COLUMN[coordinate]) {
            return 5;
        } else if(BoardUtils.SIXTH_COLUMN[coordinate]) {
            return 6;
        } else if(BoardUtils.SEVENTH_COLUMN[coordinate]) {
            return 7;
        } else if(BoardUtils.EIGHTH_COLUMN[coordinate]) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(final int coordinate) {
        if(BoardUtils.EIGHTH_RANK[coordinate]) {
            return 1;
        } else if(BoardUtils.SEVENTH_RANK[coordinate]) {
            return 2;
        } else if(BoardUtils.SIXTH_RANK[coordinate]) {
            return 3;
        } else if(BoardUtils.FIFTH_RANK[coordinate]) {
            return 4;
        } else if(BoardUtils.FOURTH_RANK[coordinate]) {
            return 5;
        } else if(BoardUtils.THIRD_RANK[coordinate]) {
            return 6;
        } else if(BoardUtils.SECOND_RANK[coordinate]) {
            return 7;
        } else if(BoardUtils.FIRST_RANK[coordinate]) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    static class KingDistance {

        final Piece enemyPiece;
        final int distance;

        KingDistance(final Piece enemyDistance,
                     final int distance) {
            this.enemyPiece = enemyDistance;
            this.distance = distance;
        }

        public Piece getEnemyPiece() {
            return enemyPiece;
        }

        public int getDistance() {
            return distance;
        }

        public int tropismScore() {
            return (enemyPiece.getPieceValue()/10) * distance;
        }

    }

}
