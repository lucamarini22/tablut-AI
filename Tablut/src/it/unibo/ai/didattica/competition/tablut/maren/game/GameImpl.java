package it.unibo.ai.didattica.competition.tablut.maren.game;

import java.util.List;
import aima.core.search.adversarial.Game;

public class GameImpl<S, A, P> implements Game<S, A, P>{
    @Override
    public S getInitialState() {
        return null;
    }

    @Override
    public P[] getPlayers() {
        return (P[]) new Object[0];
    }

    @Override
    public P getPlayer(Object o) {
        return null;
    }

    @Override
    public List<A> getActions(Object o) {
        return null;
    }

    @Override
    public S getResult(Object o, Object o2) {
        return null;
    }

    @Override
    public boolean isTerminal(Object o) {
        return false;
    }

    @Override
    public double getUtility(Object o, Object o2) {
        return 0;
    }
}
