package it.unibo.ai.didattica.competition.tablut.maren.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface Board {

    void initializeBoard();

    State.Pawn getBoardCell(int row, int col);

    void setBoard(State.Pawn[][] newBoard);
}
