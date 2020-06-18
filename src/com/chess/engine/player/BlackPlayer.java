package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.engine.pieces.Piece.PieceType.ROOK;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        if (!hasCastleOpportunities()) {
            return Collections.emptyList();
        }
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck()) {
            //blacks king side castle
            if (this.board.getTilePiece(5) == null && this.board.getTilePiece(6) == null) {
                final Piece kingSideRook = this.board.getTilePiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 5));

                    }
                }
            }
            //blacks queen side castle
            if (this.board.getTilePiece(1) == null && this.board.getTilePiece(2) == null &&
                    this.board.getTilePiece(3) == null) {
                final Piece queenSideRook = this.board.getTilePiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 3));
                    }
                }
            }
        }
//        if (kingCastles.size() > 0) {
//            System.out.println("black " + kingCastles.get(0).getCurrent() + " to " + kingCastles.get(0).getDestination());
//        }
        return kingCastles;
    }

    @Override
    public String toString() {
        return "Black";
    }
}
