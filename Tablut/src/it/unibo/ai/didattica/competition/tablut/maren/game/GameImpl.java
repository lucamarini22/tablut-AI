package it.unibo.ai.didattica.competition.tablut.maren.game;

import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;


public class GameImpl implements Game<GameState, Action, State.Turn> {

    private GameState gameState;

    public GameImpl() {
        this.gameState = new GameStateImpl();
    }

    @Override
    public GameState getInitialState() {
        return this.gameState;
    }

    @Override
    public State.Turn[] getPlayers() {
        return new State.Turn[] {State.Turn.WHITE, State.Turn.BLACK};
    }

    @Override
    public State.Turn getPlayer(GameState gameState) {
        return gameState.getTurn();
    }

    @Override
    public List<Action> getActions(GameState gameState) {
        return null;
    }

    @Override
    public GameState getResult(GameState gameState, Action action) {
        return null;
    }

    @Override
    public boolean isTerminal(GameState gameState) {
        return false;
    }

    @Override
    public double getUtility(GameState gameState, State.Turn turn) {
        return 0;
    }
}
