package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinates;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleTiles();

    private static Map<Integer, EmptyTile> createAllPossibleTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile((i)));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinates, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinates, piece) : EMPTY_TILES_CACHE.get(tileCoordinates);
    }

    private Tile(int tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
    }

    public abstract boolean occupied();

    public abstract Piece getPiece();

    public int getTileCoordinates() {
        return this.tileCoordinates;
    }

    public static final class EmptyTile extends Tile {

        EmptyTile(int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean occupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean occupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }

}
