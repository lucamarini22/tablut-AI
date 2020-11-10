package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface Board {

    void initializeBoard();

    State.Pawn getCell(int row, int col);

    void setCell(int row, int col, State.Pawn p);

    void setBoard(State.Pawn[][] newBoard);

    // boolean canMoveInto(int row, int col)
}
