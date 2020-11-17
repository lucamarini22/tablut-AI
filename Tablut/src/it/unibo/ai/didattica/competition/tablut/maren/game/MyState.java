package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;


import java.util.List;

public interface MyState {

    State.Turn getTurn();

    void setTurn(State.Turn turn);

    List<MyAction> getPossibleActions();

    int getCurrentDepth();

    void setCurrentDepth(int currentDepth);

    MyState updateState(State currentState);

    MyState getMyStateSnapshot();

    void setMyBoard(Board board);

    void applyAction(MyAction action);

    void printBoard();

    boolean isWhiteTurn();

    int getNumOf(State.Pawn pawnType);

    int whiteWon();


}
