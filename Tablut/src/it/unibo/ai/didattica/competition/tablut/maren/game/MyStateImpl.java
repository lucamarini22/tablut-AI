package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import java.util.ArrayList;
import java.util.List;

public class MyStateImpl implements MyState {

    private static final int NUM_OF_NEIGHBOURS = 4;
    private Board board;
    private State.Turn turn;
    private int currentDepth;
    private static final int DEPTH = 4;

    public MyStateImpl(int depth) {
        this.setTurn(State.Turn.WHITE);
        this.board = new BoardImpl();
        this.setCurrentDepth(depth);
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }


    @Override
    public MyState updateState(State currentState) {
        MyState updatedState = new MyStateImpl(DEPTH);
        Board updatedBoard = new BoardImpl();
        updatedBoard.setBoard(currentState.getBoard());
        updatedBoard.setWhitePositions(this.board.getWhitePositionsFromBoard(updatedBoard.getBoard()));
        updatedBoard.setBlackPositions(this.board.getBlackPositionsFromBoard(updatedBoard.getBoard()));
        updatedState.setMyBoard(updatedBoard);
        updatedState.setTurn(currentState.getTurn());
        return updatedState;
    }

    @Override
    public MyState getMyStateSnapshot() {
        MyState snapshot = new MyStateImpl(this.getCurrentDepth());
        // clone the board and pass it to the new snapshot state
        Board boardSnapshot = new BoardImpl();
        boardSnapshot.setBoard(this.board.getBoard());
        boardSnapshot.setWhitePositions(this.board.getWhitePositions());
        boardSnapshot.setBlackPositions(this.board.getBlackPositions());
        snapshot.setMyBoard(boardSnapshot);
        snapshot.setTurn(this.getTurn());
        snapshot.setCurrentDepth(this.getCurrentDepth());
        return snapshot;
    }

    @Override
    public void setMyBoard(Board board) {
        this.board = board;
    }

    public void printBoard() {
        this.board.printBoard();
    }

    @Override
    public State.Turn getTurn() {
        return this.turn;
    }

    @Override
    public void setTurn(State.Turn turn) {
        this.turn = turn;
    }

    @Override
    public List<MyAction> getPossibleActions() {
        /* get white and black pos from boardImpl.
           Then for each pos get all horizontal (right and left) and vertical (up and down) cells,
           and for each cell
                try to understand if the white pawn or the black pawn considered can move into that cell (check if it's a
                special area (for the special area use getSquareType from BoardImpl) or if there's another pawn.
                    If it can move there, then add the move into the List of MyAction that has to be returned,
                    if not, do not add (and if not, because there's another pawn, then stop to go to that direction)
         */

        List<MyAction> allPossibleActions = new ArrayList<>();
        List<Pair<Integer, Integer>> whitePositions = this.board.getWhitePositions();
        List<Pair<Integer, Integer>> blackPositions = this.board.getBlackPositions();

        if (this.isWhiteTurn()) {
            // Get all possible white actions
            whitePositions.forEach(wp -> {
                // Get all possible horizontal left actions for white Pawns
                List<Pair<Integer, Integer>> horLeftCells = this.board.getHorizontalLeftCells(wp.getFirst(), wp.getSecond());
                this.addAllPossibleWhiteActionsInOneDirection(allPossibleActions, horLeftCells, wp);

                // Get all possible horizontal actions
                List<Pair<Integer, Integer>> horRightCells = this.board.getHorizontalRightCells(wp.getFirst(), wp.getSecond());
                this.addAllPossibleWhiteActionsInOneDirection(allPossibleActions, horRightCells, wp);

                List<Pair<Integer, Integer>> vertUpCells = this.board.getVerticalUpCells(wp.getFirst(), wp.getSecond());
                this.addAllPossibleWhiteActionsInOneDirection(allPossibleActions, vertUpCells, wp);

                List<Pair<Integer, Integer>> verDownCells = this.board.getVerticalDownCells(wp.getFirst(), wp.getSecond());
                this.addAllPossibleWhiteActionsInOneDirection(allPossibleActions, verDownCells, wp);
            });
        } else {
            // Get all possible black actions
            blackPositions.forEach(bp -> {
                // Get all possible horizontal left actions
                List<Pair<Integer, Integer>> horLeftCells = this.board.getHorizontalLeftCells(bp.getFirst(), bp.getSecond());
                this.addAllPossibleBlackActionsInOneDirection(allPossibleActions, horLeftCells, bp);

                // Get all possible horizontal right actions
                List<Pair<Integer, Integer>> horRightCells = this.board.getHorizontalRightCells(bp.getFirst(), bp.getSecond());
                this.addAllPossibleBlackActionsInOneDirection(allPossibleActions, horRightCells, bp);

                // Get all possible vertical up actions
                List<Pair<Integer, Integer>> vertUpCells = this.board.getVerticalUpCells(bp.getFirst(), bp.getSecond());
                this.addAllPossibleBlackActionsInOneDirection(allPossibleActions, vertUpCells, bp);

                // Get all possible vertical up actions
                List<Pair<Integer, Integer>> vertDownCells = this.board.getVerticalDownCells(bp.getFirst(), bp.getSecond());
                this.addAllPossibleBlackActionsInOneDirection(allPossibleActions, vertDownCells, bp);
            });
        }
        // Print all the possible positions
        // allPossibleActions.forEach(System.out::println);
        //System.out.println(allPossibleActions.size());
        return allPossibleActions;
    }

    @Override
    public void applyAction(MyAction action) {
        // Modify the Pawn position of Board, and update black and white Pos
        if (this.getCurrentDepth() > 0) {
            // Update white position
            // this.board.setCell(action.getRowFrom(), action.getColumnFrom(), State.Pawn.EMPTY);
            if (this.isWhiteTurn()) {
                this.board.updateWhitePos(action.getRowFrom(), action.getColumnFrom(), action.getRowTo(), action.getColumnTo());
                if (this.board.getCell(action.getRowFrom(), action.getColumnFrom()).equals(State.Pawn.KING)) {
                    this.board.setCell(action.getRowTo(), action.getColumnTo(), State.Pawn.KING);
                } else {
                    this.board.setCell(action.getRowTo(), action.getColumnTo(), State.Pawn.WHITE);
                }
                this.board.setCell(action.getRowFrom(), action.getColumnFrom(), State.Pawn.EMPTY);
                this.setTurn(State.Turn.BLACK);
            } else {
                // Update black position
                this.board.updateBlackPos(action.getRowFrom(), action.getColumnFrom(), action.getRowTo(), action.getColumnTo());
                this.board.setCell(action.getRowTo(), action.getColumnTo(), State.Pawn.BLACK);
                this.board.setCell(action.getRowFrom(), action.getColumnFrom(), State.Pawn.EMPTY);
                this.setTurn(State.Turn.WHITE);
            }
            this.applyAnyCapture(action);
        }
    }

    private boolean isWhiteTurn() {
        return  this.getTurn().equals(State.Turn.WHITE);
    }

    private void addAllPossibleWhiteActionsInOneDirection(List<MyAction> allPossibleActions, List<Pair<Integer
            , Integer>> cellsInDirection, Pair<Integer, Integer> wp) {
        for (Pair<Integer, Integer> cell : cellsInDirection) {
            // If there's a Pawn, break and consider the next white Pawn
            if (this.board.isThereAPawn(cell.getFirst(), cell.getSecond())) {
                break;
            }
            // If the cell is not a castle and it is not a camp
            if ((!this.board.isCastle(cell.getFirst(), cell.getSecond()))
                    && (!this.board.isCamp(cell.getFirst(), cell.getSecond()))) {
                allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                        this.board.fromIntToLetter(cell.getSecond()) + (cell.getFirst() + 1), this.getTurn()));
            }
        }
    }

    private void addAllPossibleBlackActionsInOneDirection(List<MyAction> allPossibleActions, List<Pair<Integer
            , Integer>> cellsInDirection, Pair<Integer, Integer> bp){
        for (Pair<Integer, Integer> cell : cellsInDirection) {
            // If there's a Pawn, break and consider the next black Pawn
            if ( this.board.isThereAPawn(cell.getFirst(), cell.getSecond())) {
                break;
            }
            // If the cell is not a castle
            if ( (! this.board.isCastle(cell.getFirst(), cell.getSecond()))) {
                if (this.board.isCamp(cell.getFirst(), cell.getSecond())) {
                    // If in the cell is a camp, check whether the black Pawn was already outside the Camp or not
                    if (this.board.isCamp(bp.getFirst(), bp.getSecond())) {
                        // If the black was already in the camp, then he can move into the camp
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                this.board.fromIntToLetter(cell.getSecond()) + (cell.getFirst() + 1), this.getTurn()));
                    }
                } else {
                    // If in the cell is a camp, then I can move into it (I add it into the possible actions)
                    allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                            this.board.fromIntToLetter(cell.getSecond()) + (cell.getFirst() + 1), this.getTurn()));
                }
            }
        }
    }

    private void applyAnyCapture(MyAction action) {
        int rowTo = action.getRowTo();
        int colTo = action.getColumnTo();
        int rowFrom = action.getRowFrom();
        int colFrom = action.getColumnFrom();

        if (this.isWhiteTurn()) {
            standardCapture(rowTo, colTo, State.Pawn.WHITE, State.Pawn.BLACK);
            castleOrCampCapture(rowFrom, colFrom, rowTo,colTo, State.Pawn.BLACK);
        } else {
            standardCapture(rowTo, colTo, State.Pawn.BLACK, State.Pawn.WHITE);
            castleOrCampCapture(rowFrom, colFrom, rowTo,colTo, State.Pawn.WHITE);


        }
    }

    private void standardCapture(int rowTo, int colTo, State.Pawn myPawnType, State.Pawn enemyPawnType) {
        // Right standard capture
        if (this.board.getCell(rowTo, colTo + 1).equals(enemyPawnType)
                && this.board.getCell(rowTo, colTo + 2).equals(myPawnType)) {
            this.board.setCell(rowTo, colTo + 1, State.Pawn.EMPTY);
        }
        // Left standard capture
        if (this.board.getCell(rowTo, colTo - 1).equals(enemyPawnType)
                && this.board.getCell(rowTo, colTo - 2).equals(myPawnType)) {
            this.board.setCell(rowTo, colTo - 1, State.Pawn.EMPTY);
        }
        // Down standard capture
        if (this.board.getCell(rowTo + 1, colTo).equals(enemyPawnType)
                && this.board.getCell(rowTo + 2, colTo).equals(myPawnType)) {
            this.board.setCell(rowTo + 1, colTo, State.Pawn.EMPTY);
        }
        // Up standard capture
        if (this.board.getCell(rowTo - 1, colTo).equals(enemyPawnType)
                && this.board.getCell(rowTo - 2, colTo).equals(myPawnType)) {
            this.board.setCell(rowTo - 1, colTo, State.Pawn.EMPTY);
        }
    }

    private void castleOrCampCapture(int rowFrom, int colFrom, int rowTo, int colTo, State.Pawn enemyPawnType) {
        int distX = colTo - colFrom;
        int distY = rowTo - rowFrom;
        // Horizontal move
        if (distX != 0) {
            if (distX > 0) {
                // Right move
                if (this.board.getCell(rowTo, colTo + 1).equals(enemyPawnType)
                        && ( this.board.getSquareType(rowTo, colTo + 2).equals(BoardImpl.SquareType.CAMP)
                            || this.board.getSquareType(rowTo, colTo + 2).equals(BoardImpl.SquareType.CASTLE) )  ) {
                    // There are an enemy and a camp or a castle, therefore it's a capture
                    this.board.setCell(rowTo, colTo + 1, State.Pawn.EMPTY);
                }
            } else {
                // Left move
                if (this.board.getCell(rowTo, colTo - 1).equals(enemyPawnType)
                        && ( this.board.getSquareType(rowTo, colTo - 2).equals(BoardImpl.SquareType.CAMP)
                        || this.board.getSquareType(rowTo, colTo - 2).equals(BoardImpl.SquareType.CASTLE) )  ) {
                    // There are an enemy and a camp or a castle, therefore it's a capture
                    this.board.setCell(rowTo, colTo - 1, State.Pawn.EMPTY);
                }
            }
        }
        // Vertical move
        if (distY != 0) {
            if (distY > 0) {
                // Down move
                if (this.board.getCell(rowTo + 1, colTo).equals(enemyPawnType)
                        && ( this.board.getSquareType(rowTo + 2, colTo).equals(BoardImpl.SquareType.CAMP)
                        || this.board.getSquareType(rowTo + 2, colTo).equals(BoardImpl.SquareType.CASTLE) )  ) {
                    // There are an enemy and a camp or a castle, therefore it's a capture
                    this.board.setCell(rowTo + 1, colTo, State.Pawn.EMPTY);
                }
            } else {
                // Up move
                if (this.board.getCell(rowTo - 1, colTo).equals(enemyPawnType)
                        && ( this.board.getSquareType(rowTo - 2, colTo).equals(BoardImpl.SquareType.CAMP)
                        || this.board.getSquareType(rowTo - 2, colTo).equals(BoardImpl.SquareType.CASTLE) )  ) {
                    // There are an enemy and a camp or a castle, therefore it's a capture
                    this.board.setCell(rowTo - 1, colTo, State.Pawn.EMPTY);
                }
            }
        }
    }

    @Override
    public int getCurrentDepth() {
        return this.currentDepth;
    }
}