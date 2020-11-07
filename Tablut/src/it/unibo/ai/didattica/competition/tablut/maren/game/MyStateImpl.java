package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class MyStateImpl implements MyState {

    private static final int WIDTH = 9;
    private static final int KING_X = 4;
    private static final int KING_Y = 4;
    private static final int NUM_OF_WHITES_PER_ROW = 4;

    private static final LinkedList<Integer> WHITE_POS = new LinkedList<>(Arrays.asList(2, 3, 5, 6));
    private static final LinkedList<Integer> BLACK_EDGES_1 = new LinkedList<>(Arrays.asList(3, 4, 5));
    private static final LinkedList<Integer> BLACK_EDGES_2 = new LinkedList<>(Arrays.asList(0, 8));

    private State.Turn turn;


    State.Pawn[][] board;

    public MyStateImpl() {
        this.turn = State.Turn.WHITE;
        this.initializeBoard();
    }


    @Override
    public State.Turn getTurn() {
        return this.turn;
    }

    @Override
    public LinkedList<MyAction> getPossibleActions() {
        MyAction a = new MyActionImpl(); //("3e", "3f", this.getTurn());
        MyAction b = new MyActionImpl(); //("4e", "4f", this.getTurn());
        return new LinkedList<MyAction>(Arrays.asList(a, b));
    }

    private void setBoardCell(int row, int col, State.Pawn p) {
        if (!(row < 0 || col < 0 || row > WIDTH || col > WIDTH)) {
            this.board[row][col] = p;
        }
    }

    private void setWhitePositions() {
        WHITE_POS.forEach((pos) -> {
            this.setBoardCell(pos, NUM_OF_WHITES_PER_ROW, State.Pawn.WHITE);
            this.setBoardCell(NUM_OF_WHITES_PER_ROW, pos, State.Pawn.WHITE);
        });
    }

    private void setBlackPositions() {
        BLACK_EDGES_1.forEach((b_edge_1) ->
                BLACK_EDGES_2.forEach((b_edge_2) -> {
                    this.setBoardCell(b_edge_1, b_edge_2, State.Pawn.BLACK);
                    this.setBoardCell(b_edge_2, b_edge_1, State.Pawn.BLACK);
                }));
    }

    private void initializeBoard() {
        this.board = new State.Pawn[WIDTH][WIDTH];

        IntStream.range(0, WIDTH).forEach((row) -> {
            IntStream.range(0, WIDTH).forEach((col) -> {
                this.setBoardCell(row, col, State.Pawn.EMPTY);
            });
        });

        this.setBoardCell(KING_X, KING_Y, State.Pawn.THRONE);

        // this.turn = State.Turn.BLACK;

        this.setBoardCell(KING_X, KING_Y, State.Pawn.KING);
        this.setWhitePositions();
        this.setBlackPositions();

        /*this.board[2][4] = State.Pawn.WHITE;
        this.board[3][4] = State.Pawn.WHITE;
        this.board[5][4] = State.Pawn.WHITE;
        this.board[6][4] = State.Pawn.WHITE;
        this.board[4][2] = State.Pawn.WHITE;
        this.board[4][3] = State.Pawn.WHITE;
        this.board[4][5] = State.Pawn.WHITE;
        this.board[4][6] = State.Pawn.WHITE;*/
        /*this.board[0][3] = State.Pawn.BLACK;
        this.board[0][4] = State.Pawn.BLACK;
        this.board[0][5] = State.Pawn.BLACK;
        this.board[1][4] = State.Pawn.BLACK;
        this.board[8][3] = State.Pawn.BLACK;
        this.board[8][4] = State.Pawn.BLACK;
        this.board[8][5] = State.Pawn.BLACK;
        this.board[7][4] = State.Pawn.BLACK;
        this.board[3][0] = State.Pawn.BLACK;
        this.board[4][0] = State.Pawn.BLACK;
        this.board[5][0] = State.Pawn.BLACK;
        this.board[4][1] = State.Pawn.BLACK;
        this.board[3][8] = State.Pawn.BLACK;
        this.board[4][8] = State.Pawn.BLACK;
        this.board[5][8] = State.Pawn.BLACK;
        this.board[4][7] = State.Pawn.BLACK;*/
    }




}
