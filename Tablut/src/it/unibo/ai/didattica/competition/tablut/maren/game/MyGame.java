package it.unibo.ai.didattica.competition.tablut.maren.game;

public interface MyGame<S, A, P> extends aima.core.search.adversarial.Game<S, A, P>{
    void setCurrentDepth(S state, int depth);
}
