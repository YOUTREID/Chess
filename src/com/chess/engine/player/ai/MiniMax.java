package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.NullMove;
import com.chess.engine.player.MoveTransition;

import java.util.concurrent.atomic.AtomicLong;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;
    private long boardsEvaluated;
    private FreqTableRow[] freqTable;
    private int freqTableIndex;

    public MiniMax(final int searchDepth) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = new NullMove();

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " thinking with depth = " + this.searchDepth);

        this.freqTable = new FreqTableRow[board.currentPlayer().getLegalMoves().size()];
        this.freqTableIndex = 0;
        int moveCounter = 1;
        int numMoves = board.currentPlayer().getLegalMoves().size();

        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final FreqTableRow row = new FreqTableRow(move);
                this.freqTable[this.freqTableIndex] = row;
                currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getToBoard(), searchDepth - 1) :
                        max(moveTransition.getToBoard(), searchDepth - 1);
                this.freqTableIndex++;
                if (board.currentPlayer().getAlliance().isWhite() &&
                        currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getAlliance().isBlack() &&
                        currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            } else {
                System.out.println("\t" + toString() + " can't execute move (" +moveCounter+ "/" +numMoves+ ") " + move);
            }
            moveCounter++;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s SELECTS %s [#boards = %d time taken = %d ms, rate = %.1f\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, executionTime, (1000 * ((double)this.boardsEvaluated/ executionTime)));
        long total = 0;
        for (final FreqTableRow row : this.freqTable) {
            if(row != null) {
                total += row.getCount();
            }
        }
        if(this.boardsEvaluated != total) {
            System.out.println("somethings wrong with the # of boards evaluated!");
        }
        return bestMove;
    }

    private int min(Board board, final int depth) {
        if (depth == 0) {
            this.boardsEvaluated++;
            this.freqTable[this.freqTableIndex].increment();
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move: board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getToBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }

    private int max(Board board, final int depth) {
        if (depth == 0 || isEndGameScenario(board)) {
            this.boardsEvaluated++;
            this.freqTable[this.freqTableIndex].increment();
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move: board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getToBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private static class FreqTableRow {

        private final Move move;
        private final AtomicLong count;

        FreqTableRow(final Move move) {
            this.count = new AtomicLong();
            this.move = move;
        }

        long getCount() {
            return this.count.get();
        }

        void increment() {
            this.count.incrementAndGet();
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.move.getCurrent()) +
                    BoardUtils.getPositionAtCoordinate(this.move.getDestination()) + " : " +this.count;
        }
    }
}
