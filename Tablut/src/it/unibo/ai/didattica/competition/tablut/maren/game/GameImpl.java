package it.unibo.ai.didattica.competition.tablut.maren.game;

import java.util.List;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public class GameImpl implements MyGame<MyState, MyAction, State.Turn> {

    private static final int B_BLACK_WON = 200000000;
    private static final int B_NUM_OF_WHITE = - 50000;
    private static final int B_NUM_OF_BLACK = 20000;
    private static final int B_DIST_KING_ESCAPE = 10;
    private static final int B_WHITE_WON = - 2000000;
    private static final int B_NUM_OPP_NEXT_KING = - 5000;



    private final MyState myState;

    public GameImpl(int depth) {
        this.myState = new MyStateImpl(depth);
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
        MyState resultState = myState.getMyStateSnapshot();
        resultState.applyAction(action);
        return resultState;
    }

    @Override
    public boolean isTerminal(MyState myState) {
        return myState.getCurrentDepth() == 0 || myState.getTurn() == State.Turn.BLACKWIN || myState.getTurn() == State.Turn.WHITEWIN;
    }

    @Override
    public double getUtility(MyState myState, State.Turn turn) {
        if (turn.equals(State.Turn.WHITE)) {
            return 2000000000 * myState.whiteWon() +
                    200000 * myState.getNumOf(State.Pawn.WHITE) +
                    - 100000 * myState.getNumOf(State.Pawn.BLACK)
                    - 100 * myState.getDistanceFromKingToEscape()
                    - 20000000 * myState.blackWon()
                    - 50000 * myState.getNumOfOpponentsNextToTheKingOf(State.Turn.WHITE);
            /*return 50000 * myState.whiteWon() +
                    250 * myState.getNumOf(State.Pawn.WHITE) +
                    - 164 * myState.getNumOf(State.Pawn.BLACK)
                    - 42 * myState.getDistanceFromKingToEscape()
                    - 5000 * myState.blackWon()
                    - 147 * myState.getNumOfOpponentsNextToTheKing(State.Turn.WHITE);*/

        } else if (turn.equals(State.Turn.BLACK)) {
            /*return 2000000000 * myState.blackWon() +
                    - 220000 * myState.getNumOf(State.Pawn.WHITE) +
                    200000 * myState.getNumOf(State.Pawn.BLACK) +
                    100 * myState.getDistanceFromKingToEscape()
                    - 2000000000 * myState.whiteWon();*/
            return B_BLACK_WON * myState.blackWon() +
                    B_NUM_OF_WHITE * myState.getNumOf(State.Pawn.WHITE) +
                    B_NUM_OF_BLACK * myState.getNumOf(State.Pawn.BLACK) +
                    B_DIST_KING_ESCAPE * myState.getDistanceFromKingToEscape() +
                    B_WHITE_WON * myState.whiteWon() +
                    B_NUM_OPP_NEXT_KING * myState.getNumOfOpponentsNextToTheKingOf(State.Turn.BLACK);
        }
        return 0;
    }

    public void setCurrentDepth(MyState myState, int depth) {
         myState.setCurrentDepth(depth);
    }

}