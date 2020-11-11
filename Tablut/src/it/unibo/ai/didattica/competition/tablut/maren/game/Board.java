package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.List;

public interface Board {

    void initializeBoard();

    State.Pawn getCell(int row, int col);

    BoardImpl.SquareType getSquareType(int row, int col);

    void setCell(int row, int col, State.Pawn p);

    void setBoard(State.Pawn[][] newBoard);

    List<Pair<Integer, Integer>> getHorizontalLeftCells(int row, int col);

    List<Pair<Integer, Integer>> getHorizontalRightCells(int row, int col);

    List<Pair<Integer, Integer>> getVerticalUpCells(int row, int col);

    List<Pair<Integer, Integer>> getVerticalDownCells(int row, int col);

    List<Pair<Integer, Integer>> getWhitePositions();

    List<Pair<Integer, Integer>> getBlackPositions();

    // changeWhitePos -> cambia la posizione del bianco sia nella Pawn[][] che nella lista delle pos dei bianchi ?

    // changeBlackPos -> "  "   "       "      " nero   "       "               "                   "  " neri ?

    // boolean canMoveInto(int row, int col) -> va in myState
}
