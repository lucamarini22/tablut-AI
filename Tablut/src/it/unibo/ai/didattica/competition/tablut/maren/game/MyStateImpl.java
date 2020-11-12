package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyStateImpl implements MyState {

    private final Board board;
    private State.Turn turn;
    private int currentDepth;

    public MyStateImpl(int depth) {
        this.turn = State.Turn.WHITE;
        this.board = new BoardImpl();
        this.setCurrentDepth(depth);
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }


    @Override
    public void updateState(State currentState) {
        this.board.setBoard(currentState.getBoard());
    }


    @Override
    public State.Turn getTurn() {
        return this.turn;
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

        whitePositions.forEach(wp -> {
            List<Pair<Integer, Integer>> horLeftCells = this.board.getHorizontalLeftCells(wp.getFirst(), wp.getSecond());
            horLeftCells.forEach((hor_l_c) -> {
                // If the cell is not a camp and is not a castle, then I can move
                if ((! this.board.isCamp(hor_l_c.getFirst(), hor_l_c.getSecond()))
                        && (! this.board.isCastle(hor_l_c.getFirst(), hor_l_c.getSecond()))) {
                    // If in the cell there's not another Pawn add the cell to the possible actions
                    if (! (this.board.isThereAPawn(hor_l_c.getFirst(), hor_l_c.getSecond())) ) {
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                                this.board.fromIntToLetter(hor_l_c.getSecond()) + (hor_l_c.getFirst() + 1), this.getTurn()));
                    }
                }
            });

            List<Pair<Integer, Integer>> horRightCells = this.board.getHorizontalRightCells(wp.getFirst(), wp.getSecond());

            List<Pair<Integer, Integer>> vertUpCells = this.board.getVerticalUpCells(wp.getFirst(), wp.getSecond());

            List<Pair<Integer, Integer>> verDownCells = this.board.getVerticalDownCells(wp.getFirst(), wp.getSecond());

        });

        // do the same for blackPos!!






        List l = new LinkedList<>();
        MyAction a = new MyActionImpl("e3", "f3", this.getTurn());
        MyAction b = new MyActionImpl("e4", "f4", this.getTurn());
        l.add(a);
        l.add(b);
        return l;
    }

    @Override
    public int getCurrentDepth() {
        return this.currentDepth;
    }
}
