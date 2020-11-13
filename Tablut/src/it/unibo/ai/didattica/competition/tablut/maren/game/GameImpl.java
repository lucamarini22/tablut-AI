package it.unibo.ai.didattica.competition.tablut.maren.game;

import java.util.LinkedList;
import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class GameImpl implements MyGame<MyState, MyAction, State.Turn> {

    private final MyState myState;

    public GameImpl(int depth) {
        this.myState = new MyStateImpl(depth);
    }

    @Override
    public MyState getInitialState() {
        return this.myState;
    }

    @Override
    public State.Turn[] getPlayers() {
        return new State.Turn[] {State.Turn.WHITE, State.Turn.BLACK};
    }

    @Override
    public State.Turn getPlayer(MyState myState) {
        return myState.getTurn();
    }

    @Override
    public List<MyAction> getActions(MyState myState) {
        return myState.getPossibleActions();
    }

    @Override
    public MyState getResult(MyState myState, MyAction action) {
        MyState resultState = myState.getMyStateSnapshot();
        resultState.applyAction(action);
        return resultState;
    }

    @Override
    public boolean isTerminal(MyState myState) {
        return myState.getCurrentDepth() == 0;
    }

    @Override
    public double getUtility(MyState myState, State.Turn turn) {
        return 0;
    }

    public void setCurrentDepth(MyState myState, int depth) {
         myState.setCurrentDepth(depth);
    }


}
