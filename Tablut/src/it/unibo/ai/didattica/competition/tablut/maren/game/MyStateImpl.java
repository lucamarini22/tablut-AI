package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;
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
