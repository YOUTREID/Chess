public abstract class Tile {

    int tileCoordinates;

    Tile(int tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
    }

    public abstract boolean occupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {

        EmptyTile(int coordinate) {
            super(coordinate);
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

        Piece pieceOnTile;

        OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
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
