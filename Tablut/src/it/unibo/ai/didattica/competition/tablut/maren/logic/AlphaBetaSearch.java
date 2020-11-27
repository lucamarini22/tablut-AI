package it.unibo.ai.didattica.competition.tablut.maren.logic;

import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyAction;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyGame;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlphaBetaSearch {

    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";

    MyGame<MyState, MyAction, State.Turn> game;
    private Metrics metrics = new Metrics();
    private final int depth;
    private final Random rand = new Random();
    private final List<MyAction> bestActions = new ArrayList<>();

    public AlphaBetaSearch(MyGame<MyState, MyAction, State.Turn> game, int depth, int timeout) {
        this.game = game;
        this.depth = depth;
    }

    public MyAction makeDecision(MyState state) {
        this.bestActions.clear();
        metrics = new Metrics();
        MyAction result = null;
        int depth = this.depth;
        double resultValue = Double.NEGATIVE_INFINITY;
        State.Turn player = game.getPlayer(state);
        for (MyAction action : game.getActions(state)) {
            double value = minValue(game.getResult(state, action), player,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth - 1);
            if (value == resultValue) {
                this.bestActions.add(action);
                System.out.println("Best moves: " + Arrays.toString(this.bestActions.toArray()));
                System.out.println("\n");
            } else if (value > resultValue) {
                resultValue = value;
                this.bestActions.clear();
                this.bestActions.add(action);
                System.out.println("Best moves: " + Arrays.toString(this.bestActions.toArray()));
                System.out.println("\n");
            }
        }
        if(this.bestActions.size() > 0) {
            result = this.bestActions.get(rand.nextInt(this.bestActions.size()));
        }
        return result;
    }

    public double maxValue(MyState state, State.Turn player, double alpha, double beta, int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        this.game.setCurrentDepth(state, depth);
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (MyAction action : game.getActions(state)) {
            value = Math.max(value, minValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public double minValue(MyState state, State.Turn player, double alpha, double beta, int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        this.game.setCurrentDepth(state, depth);
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        }
        double value = Double.POSITIVE_INFINITY;
        for (MyAction action : game.getActions(state)) {
            value = Math.min(value, maxValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value <= alpha && (value != Double.NEGATIVE_INFINITY)) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value != Double.NEGATIVE_INFINITY? value : 0;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}