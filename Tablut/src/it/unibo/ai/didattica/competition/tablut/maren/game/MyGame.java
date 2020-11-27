package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface MyGame<S, A, P> extends aima.core.search.adversarial.Game<MyState, MyAction, State.Turn>{
    void setCurrentDepth(S state, int depth);
}
