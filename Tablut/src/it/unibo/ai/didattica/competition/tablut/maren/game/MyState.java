package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.io.IOException;
import java.util.LinkedList;

public interface MyState {

    State.Turn getTurn();

    LinkedList<MyAction> getPossibleActions();


}
