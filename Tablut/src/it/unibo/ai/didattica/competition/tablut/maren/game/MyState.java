package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface MyState {

    State.Turn getTurn();

    List<MyAction> getPossibleActions();

    int getCurrentDepth();

    void setCurrentDepth(int currentDepth);

    void updateState(State currentState);




}
