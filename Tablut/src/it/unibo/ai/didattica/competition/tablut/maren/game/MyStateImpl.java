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
