package it.unibo.ai.didattica.competition.tablut.maren.game;

import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class GameImpl implements Game<MyState, MyAction, State.Turn> {

    private MyState myState;

    public GameImpl() {
        this.myState = new MyStateImpl();
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
        return null;
    }

    @Override
    public boolean isTerminal(MyState myState) {
        return false;
    }

    @Override
    public double getUtility(MyState myState, State.Turn turn) {
        return 0;
    }
}
