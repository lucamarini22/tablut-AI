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
            private List<Pair<Integer, Integer>> whitePos = new ArrayList<>();
    private List<Pair<Integer, Integer>> blackPos = new ArrayList<>();
    private final HashMap<Integer, String> intLetterMap = new HashMap<>();

    public BoardImpl() {
        this.initializeBoard();
    }

    public enum SquareType {
        CASTLE,
        CAMP,
        ESCAPE,
        NULLTYPE
    }

    public void printBoard() {
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) {
                System.out.print(this.getBoard()[i][j]+ "|");
            }
            System.out.println("");
        }
    }

    @Override
    public int getNumOf(State.Pawn pawnType) {
        return (int) Arrays.stream(this.board).flatMap(Arrays::stream).filter((p) -> p.equals(pawnType)).count();
    }

    @Override
    public Pair<Integer, Integer> getKingPosition() {
        int row;
        int col = 0;
        for (row = 0; row < WIDTH; row++) {
            for (col = 0; col < WIDTH; col++) {
                if( this.getCell(row, col).equals(State.Pawn.KING)) {
                    return new Pair<>(row, col);
                }
            }
        }
        return null;
    }

    @Override
    public boolean isKingOnEscape() {
        Pair<Integer, Integer> kingPos = this.getKingPosition();
        if (kingPos != null) {
            return this.getSquareType(kingPos.getFirst(), kingPos.getSecond()).equals(SquareType.ESCAPE);
        } else {
            return false;
        }
    }

    public void initializeBoard() {
        this.board = new State.Pawn[WIDTH][WIDTH];

        IntStream.range(0, WIDTH).forEach((row) -> {
            IntStream.range(0, WIDTH).forEach((col) -> {
                this.setCell(row, col, State.Pawn.EMPTY);
            });
        });

        this.setCell(KING_X, KING_Y, State.Pawn.THRONE);
        this.setCell(KING_X, KING_Y, State.Pawn.KING);
        this.setInitialWhitePositions();
        this.setInitialBlackPositions();
        this.setSpecialSquares();
        this.initializeIntToLetterMap();
    }

    public State.Pawn getCell(int row, int col) {
        if (!(row < 0 || col < 0 || row > WIDTH - 1 || col > WIDTH - 1)) {
            return this.board[row][col];
        }
        return State.Pawn.NULL;
    }

    @Override
    public SquareType getSquareType(int row, int col) {
        SquareType squareType = this.specialSquares.get(new Pair<>(row, col));
        if (squareType == null) {
            return  SquareType.NULLTYPE;
        }
        return squareType;
    }

    public void setCell(int row, int col, State.Pawn p) {
        if (!(row < 0 || col < 0 || row > WIDTH - 1 || col > WIDTH - 1)) {
            this.board[row][col] = p;
        }
    }

    @Override
    public State.Pawn[][] getBoard() {
        return this.board;
    }

    public void setBoard(State.Pawn[][] newBoard) {
        IntStream.range(0, WIDTH).forEach((i) -> {
            IntStream.range(0, WIDTH).forEach((j) -> {
                this.setCell(i, j, newBoard[i][j]);
            });
        });
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
        return new ArrayList<>(this.whitePos);
    }

    @Override
    public void setWhitePositions(List<Pair<Integer, Integer>> whitePositions) {
        this.whitePos = whitePositions;

    }

    @Override
    public List<Pair<Integer, Integer>> getBlackPositions() {
        return new ArrayList<>(this.blackPos);
    }

    @Override
    public void setBlackPositions(List<Pair<Integer, Integer>> blackPositions) {
        this.blackPos = blackPositions;
    }

    @Override
    public List<Pair<Integer, Integer>> getWhitePositionsFromBoard(State.Pawn[][] board) {
        List<Pair<Integer, Integer>> whitePos = new ArrayList<>();
        IntStream.range(0, WIDTH).forEach((i) -> {
            IntStream.range(0, WIDTH).forEach((j) -> {
                if (board[i][j] == State.Pawn.WHITE || board[i][j] == State.Pawn.KING) {
                    whitePos.add(new Pair<>(i, j));
                }
            });
        });
        return whitePos;
    }

    @Override
    public List<Pair<Integer, Integer>> getBlackPositionsFromBoard(State.Pawn[][] board) {
        List<Pair<Integer, Integer>> whitePos = new ArrayList<>();
        IntStream.range(0, WIDTH).forEach((i) -> {
            IntStream.range(0, WIDTH).forEach((j) -> {
                if (board[i][j] == State.Pawn.BLACK) {
                    whitePos.add(new Pair<>(i, j));
                }
            });
        });
        return whitePos;
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

    @Override
    public void updateWhitePos(int rowFrom, int colFrom, int rowTo, int colTo) {
        this.whitePos.remove(new Pair<>(rowFrom, colFrom));
        this.whitePos.add(new Pair<>(rowTo, colTo));
    }

    @Override
    public void updateBlackPos(int rowFrom, int colFrom, int rowTo, int colTo) {
        this.blackPos.remove(new Pair<>(rowFrom, colFrom));
        this.blackPos.add(new Pair<>(rowTo, colTo));
    }

    private void setInitialWhitePositions() {
        WHITE_POS.forEach((pos) -> {
            this.setCell(pos, NUM_OF_WHITES_PER_ROW, State.Pawn.WHITE);
            this.setCell(NUM_OF_WHITES_PER_ROW, pos, State.Pawn.WHITE);
            this.whitePos.add(new Pair<>(pos, NUM_OF_WHITES_PER_ROW));
            this.whitePos.add(new Pair<>(NUM_OF_WHITES_PER_ROW, pos));
        });
        this.whitePos.add(new Pair<>(KING_X, KING_Y));
    }

    private void setInitialBlackPositions() {
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
        List<String> letters = new ArrayList<>();
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
