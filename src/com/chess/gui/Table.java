package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.AI.AlphaBeta;
import com.chess.engine.player.AI.MiniMax;
import com.chess.engine.player.AI.MoveStrategy;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table extends Observable {
    private final static Dimension OUTER_DIMENSION = new Dimension(780, 820);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Table INSTANCE = new Table();
    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final DebugPanel debugPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final GameSetup gameSetup;
    private final String imageFormat = ".png";
    private final String defaultPieceImagePath = "art/simple/";
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private Move computerMove;

    private boolean highlightLegalMoves;
    private boolean alphaBetaOn = true;

    private Table() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_DIMENSION);

        this.chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.debugPanel = new DebugPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.addObserver(new TableGameAIWatcher());
        this.gameSetup = new GameSetup(this.gameFrame, true);

        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
        this.gameFrame.setVisible(true);
    }

    public static Table get() {
        return INSTANCE;
    }

    private GameSetup getGameSetup() {
        return this.gameSetup;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private Board getGameBoard() {
        return this.chessBoard;
    }

    private DebugPanel getDebugPanel() {
        return this.debugPanel;
    }

    public void show() {
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
        Table.get().getDebugPanel().redo();
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open PGN file");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.dispose();
            }
        });
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlight = new JCheckBoxMenuItem("Highlight Legal Moves", true);
        legalMoveHighlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlight.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHighlight);
        return preferencesMenu;
    }

    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Options");
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table.get().getGameSetup().promptUser();
                Table.get().setupUpdate(Table.get().getGameSetup());
            }
        });

        optionsMenu.add(setupGameMenuItem);

        optionsMenu.addSeparator();
        final JCheckBoxMenuItem alphaBetaToggle = new JCheckBoxMenuItem("Alpha beta pruning", true);
        alphaBetaToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alphaBetaOn = alphaBetaToggle.isSelected();
            }
        });

        optionsMenu.add(alphaBetaToggle);

        return optionsMenu;
    }

    public void setupUpdate(GameSetup gameSetup) {
        setChanged();
        notifyObservers(gameSetup);
    }

    public boolean isAlphaBetaOn() {
        return alphaBetaOn;
    }

    private static class TableGameAIWatcher implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) &&
                    !Table.get().getGameBoard().currentPlayer().isInCheckMate() &&
                    !Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
                System.out.println(Table.get().getGameBoard().currentPlayer() + " is set to AI, thinking....");
                final AIThinkTank thinkTank = new AIThinkTank();
                thinkTank.execute();
            }

            if (Table.get().getGameBoard().currentPlayer().isInCheckMate() ||
                    Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
                JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
                        "Game Over: Player " + Table.get().getGameBoard().currentPlayer() + " is in checkmate!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if (Table.get().getGameBoard().currentPlayer().isInStaleMate() ||
                    Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
                JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
                        "Game Over: Player " + Table.get().getGameBoard().currentPlayer() + " is in stalemate!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public void updateGameBoard(Board board) {
        this.chessBoard = board;
    }

    public void updateComputerMove(Move move) {
        this.computerMove = move;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }

    private void moveMadeUpdate(PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    private void undoAllMoves() {
        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
        }
        this.computerMove = null;
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(chessBoard);
        Table.get().getDebugPanel().redo();
    }

    private void undoLastMove() {
        final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
        this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
        this.computerMove = null;
        Table.get().getMoveLog().removeMove(lastMove);
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(chessBoard);
        Table.get().getDebugPanel().redo();
    }

    private static class AIThinkTank extends SwingWorker<Move, String> {
        private AIThinkTank() {
        }

        @Override
        protected Move doInBackground() throws Exception {
            final Move bestMove;
//            final Move bookMove = Table.get().getUseBook()
//                    ? MySqlGamePersistence.get().getNextBestMove(Table.get().getGameBoard(),
//                    Table.get().getGameBoard().currentPlayer(),
//                    Table.get().getMoveLog().getMoves().toString().replaceAll("\\[", "").replaceAll("\\]", ""))
//                    : Move.NULL_MOVE;
//            if (Table.get().getUseBook() && bookMove != Move.NULL_MOVE) {
//                bestMove = bookMove;
//            }
            final int moveNumber = Table.get().getMoveLog().size();
            final int quiescenceFactor = 2000 + (100 * moveNumber);
            if (Table.get().isAlphaBetaOn()) {
                final AlphaBeta strategy = new AlphaBeta(quiescenceFactor); //1500
                strategy.addObserver(Table.get().getDebugPanel());
                bestMove = strategy.execute(Table.get().getGameBoard(), Table.get().getGameSetup().getSearchDepth());
                return bestMove;
            } else {
                final MiniMax miniMax = new MiniMax(Table.get().getGameSetup().getSearchDepth());
                bestMove = miniMax.execute(Table.get().getGameBoard(), Table.get().getGameSetup().getSearchDepth());
                return bestMove;
            }
        }

        @Override
        protected void done() {
            try {
                final Move bestMove = get();
                Table.get().updateComputerMove(bestMove);
                Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getToBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                Table.get().getDebugPanel().redo();
                Table.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        abstract BoardDirection opposite();
    }

    enum PlayerType {
        HUMAN,
        COMPUTER
    }

    public static class MoveLog {
        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        public void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        public void clear() {
            this.moves.clear();
        }

        public Move removeMove(final int index) {
            return this.moves.remove(index);
        }

        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {

        private final int tileID;

        TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePiece(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileID);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileID);
                            Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinates(),
                                    destinationTile.getTileCoordinates());
                            MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getToBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);
                                if (gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
                                    Table.get().moveMadeUpdate(PlayerType.HUMAN);
                                }
                                boardPanel.drawBoard(chessBoard);
                                debugPanel.redo();
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePiece(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        private void assignTilePiece(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileID).occupied()) {
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagePath +
                                    board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1) +
                                    board.getTile(this.tileID).getPiece().toString() + imageFormat));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void highlightLegals(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestination() == this.tileID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
                if (humanMovedPiece.getPieceType().isKing()) {
                    Collection<Move> pieceLegalMoves = humanMovedPiece.calculateLegalMoves(board);
                    pieceLegalMoves.addAll(board.currentPlayer().calculateKingCastles(board.getLegalMoves(), board.getOpponentMoves()));
                    return pieceLegalMoves;
                }
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileID] ||
                    BoardUtils.SIXTH_RANK[this.tileID] ||
                    BoardUtils.FOURTH_RANK[this.tileID] ||
                    BoardUtils.SECOND_RANK[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK[this.tileID] ||
                    BoardUtils.FIFTH_RANK[this.tileID] ||
                    BoardUtils.THIRD_RANK[this.tileID] ||
                    BoardUtils.FIRST_RANK[this.tileID]) {
                setBackground(this.tileID % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

    }

}
