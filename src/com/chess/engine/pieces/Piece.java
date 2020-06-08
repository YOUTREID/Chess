package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final Type pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final Type pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    protected int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece p = (Piece) other;
        return piecePosition == p.getPiecePosition() && pieceType == p.getPieceType() &&
                pieceAlliance == p.getPieceAlliance() && isFirstMove == p.isFirstMove();

    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    public int getPiecePosition() {
        return piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public Type getPieceType() {
        return this.pieceType;
    }

    public abstract int locationBonus();

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public enum Type {

        PAWN("P", 100) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N", 300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B", 300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R", 500) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q", 900) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K", 10000) {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private final String name;
        private final int pieceValue;

        Type(final String name, final int pieceValue) {
            this.name = name;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public int getPieceValue() {
            return this.pieceValue;
        }

        public abstract boolean isKing();
        public abstract boolean isPawn();
        public abstract boolean isRook();
    }

}
