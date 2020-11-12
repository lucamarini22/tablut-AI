package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.*;
import java.util.stream.IntStream;

public class BoardImpl implements Board{
    private static final int WIDTH = 9;
    private static final int KING_X = 4;
    private static final int KING_Y = 4;
    private static final int NUM_OF_WHITES_PER_ROW = 4;

    private static final LinkedList<Integer> WHITE_POS = new LinkedList<>(Arrays.asList(2, 3, 5, 6));
    private static final LinkedList<Integer> BLACK_EDGES_1 = new LinkedList<>(Arrays.asList(3, 4, 5));
    private static final LinkedList<Integer> BLACK_EDGES_2 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> INTERNAL_BLACK_1 = new LinkedList<>(Arrays.asList(1, 7));
    private static final int INTERNAL_BLACK_2 = 4;

    private static final LinkedList<Integer> CAMP_EDGES_1 = new LinkedList<>(Arrays.asList(3, 4, 5));
    private static final LinkedList<Integer> CAMP_EDGES_2 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> INTERNAL_CAMP_1 = new LinkedList<>(Arrays.asList(1, 7));
    private static final int INTERNAL_CAMP_2 = 4;

    private static final LinkedList<Integer> ESCAPES_1 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> ESCAPES_2 = new LinkedList<>(Arrays.asList(1, 2, 6, 7));

    private State.Pawn[][] board;
    private final HashMap<Pair<Integer, Integer>, BoardImpl.SquareType> specialSquares = new HashMap<>();
    private final List<Pair<Integer, Integer>> whitePos = new ArrayList<>();
    private final List<Pair<Integer, Integer>> blackPos = new ArrayList<>();

    private final HashMap<Integer, String> intLetterMap = new HashMap<>();




    public BoardImpl() {
        this.initializeBoard();
    }

    public enum SquareType {
        CASTLE,
        CAMP,
        ESCAPE
    }

    public void initializeBoard() {
        this.board = new State.Pawn[WIDTH][WIDTH];

        IntStream.range(0, WIDTH).forEach((row) -> {
            IntStream.range(0, WIDTH).forEach((col) -> {
                this.setCell(row, col, State.Pawn.EMPTY);
            });
        });

        this.setCell(KING_X, KING_Y, State.Pawn.THRONE);

        // this.turn = State.Turn.BLACK;

        this.setCell(KING_X, KING_Y, State.Pawn.KING);
        this.setWhitePositions();
        this.setBlackPositions();
        this.setSpecialSquares();
        this.initializeIntToLetterMap();

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

    public State.Pawn getCell(int row, int col) {
        if (!(row < 0 || col < 0 || row > WIDTH - 1 || col > WIDTH - 1)) {
            return this.board[row][col];
        }
        return null;
    }

    @Override
    public SquareType getSquareType(int row, int col) {
        return this.specialSquares.get(new Pair<>(row, col));
    }

    public void setCell(int row, int col, State.Pawn p) {
        if (!(row < 0 || col < 0 || row > WIDTH - 1 || col > WIDTH - 1)) {
            this.board[row][col] = p;
        }
    }

    public void setBoard(State.Pawn[][] newBoard) {
        this.board = newBoard;
    }

    @Override
    public List<Pair<Integer, Integer>> getHorizontalLeftCells(int row, int col) {
        List<Pair<Integer, Integer>> horLeftCells = new ArrayList<>();
        int p;
        for (p = col - 1; p >= 0; p--) {
            horLeftCells.add(new Pair<>(row, p));
        }
        return horLeftCells;
    }

    @Override
    public List<Pair<Integer, Integer>> getHorizontalRightCells(int row, int col) {
        List<Pair<Integer, Integer>> horRightCells = new ArrayList<>();
        IntStream.range(col + 1, WIDTH).forEach((p) -> {
            horRightCells.add(new Pair<>(row, p));

        });
        return horRightCells;
    }

    @Override
    public List<Pair<Integer, Integer>> getVerticalUpCells(int row, int col) {
        List<Pair<Integer, Integer>> verUpCells = new ArrayList<>();
        int p;
        for (p = row - 1; p >= 0; p--) {
            verUpCells.add(new Pair<>(p, col));
        }
        return verUpCells;
    }

    @Override
    public List<Pair<Integer, Integer>> getVerticalDownCells(int row, int col) {
        List<Pair<Integer, Integer>> verDownCells = new ArrayList<>();
        IntStream.range(row + 1, WIDTH).forEach((p) -> {
            verDownCells.add(new Pair<>(p, col));
        });
        return verDownCells;
    }

    @Override
    public List<Pair<Integer, Integer>> getWhitePositions() {
        return this.whitePos;
    }

    @Override
    public List<Pair<Integer, Integer>> getBlackPositions() {
        return this.blackPos;
    }

    @Override
    public boolean isCamp(int row, int col) {
        return this.getSquareType(row, col) == SquareType.CAMP;
    }

    @Override
    public boolean isEscape(int row, int col) {
        return this.getSquareType(row, col) == SquareType.ESCAPE;
    }

    @Override
    public boolean isCastle(int row, int col) {
        return this.getSquareType(row, col) == SquareType.CASTLE;
    }

    @Override
    public boolean isThereAPawn(int row, int col) {
        return ! (this.getCell(row, col) == State.Pawn.EMPTY);
    }

    @Override
    public String fromIntToLetter(int i) {
        return this.intLetterMap.get(i);
    }

    private void setWhitePositions() {
        WHITE_POS.forEach((pos) -> {
            this.setCell(pos, NUM_OF_WHITES_PER_ROW, State.Pawn.WHITE);
            this.setCell(NUM_OF_WHITES_PER_ROW, pos, State.Pawn.WHITE);
            this.whitePos.add(new Pair<>(pos, NUM_OF_WHITES_PER_ROW));
            this.whitePos.add(new Pair<>(NUM_OF_WHITES_PER_ROW, pos));
        });
        this.whitePos.add(new Pair<>(KING_X, KING_Y));
    }

    private void setBlackPositions() {
        BLACK_EDGES_1.forEach((b_edge_1) ->
                BLACK_EDGES_2.forEach((b_edge_2) -> {
                    this.setCell(b_edge_1, b_edge_2, State.Pawn.BLACK);
                    this.setCell(b_edge_2, b_edge_1, State.Pawn.BLACK);
                    this.blackPos.add(new Pair<>(b_edge_1, b_edge_2));
                    this.blackPos.add(new Pair<>(b_edge_2, b_edge_1));
                }));
        INTERNAL_BLACK_1.forEach((i_b_1) -> {
            this.setCell(i_b_1, INTERNAL_BLACK_2, State.Pawn.BLACK);
            this.setCell(INTERNAL_BLACK_2, i_b_1, State.Pawn.BLACK);
            this.blackPos.add(new Pair<>(i_b_1, INTERNAL_BLACK_2));
            this.blackPos.add(new Pair<>(INTERNAL_BLACK_2, i_b_1));
        });
    }

    private void setSpecialSquares() {
        // Set escapes
        ESCAPES_1.forEach((e1) -> {
            ESCAPES_2.forEach((e2 -> {
                this.specialSquares.put(new Pair<>(e1, e2), BoardImpl.SquareType.ESCAPE);
                this.specialSquares.put(new Pair<>(e2, e1), BoardImpl.SquareType.ESCAPE);
            }));
        });

        // Set camps
        CAMP_EDGES_1.forEach((c_edge_1) ->
                CAMP_EDGES_2.forEach((c_edge_2) -> {
                    this.specialSquares.put(new Pair<>(c_edge_1, c_edge_2), BoardImpl.SquareType.CAMP);
                    this.specialSquares.put(new Pair<>(c_edge_2, c_edge_1), BoardImpl.SquareType.CAMP);
                }));
        INTERNAL_CAMP_1.forEach((i_c_1) -> {
            this.specialSquares.put(new Pair<>(i_c_1, INTERNAL_CAMP_2), BoardImpl.SquareType.CAMP);
            this.specialSquares.put(new Pair<>(INTERNAL_CAMP_2, i_c_1), BoardImpl.SquareType.CAMP);
        });

        // Set the castle
        this.specialSquares.put(new Pair<>(KING_X, KING_Y), BoardImpl.SquareType.CASTLE);
    }

    private void initializeIntToLetterMap() {
        List<String> letters = new ArrayList<String>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        IntStream.range(0, WIDTH).forEach((num) -> {
            this.intLetterMap.put(num, letters.get(num));
        });

    }
}
