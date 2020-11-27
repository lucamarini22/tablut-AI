package it.unibo.ai.didattica.competition.tablut.maren.logic;

import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyAction;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyGame;
import it.unibo.ai.didattica.competition.tablut.maren.game.MyState;

import java.util.*;
import java.util.concurrent.*;

public class AlphaBetaSearch implements Callable<MyAction> {

    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";

    MyGame<MyState, MyAction, State.Turn> game;
    private Metrics metrics = new Metrics();
    private final int depth;
    private final Random rand = new Random();
    private final List<MyAction> bestActions = new ArrayList<>();
    private final int timeout;
    private final ExecutorService timer = Executors.newCachedThreadPool();

    private MyState currState;
    private MyAction publicResult;


    public AlphaBetaSearch(MyGame<MyState, MyAction, State.Turn> game, int depth, int timeout) {
        this.game = game;
        this.depth = depth;
        this.timeout = timeout;
    }

    public MyAction makeDecision(MyState state) {


        this.currState = state;
        Future<MyAction> timerResult = timer.submit(this);

        this.bestActions.clear();
        this.publicResult = null;

        try {
            this.publicResult = timerResult.get(this.timeout, TimeUnit.SECONDS);
            System.out.println("Selected: {" + this.publicResult.toString() + "}");
        } catch (TimeoutException e) {

            boolean cancellato = timerResult.cancel(true);
            System.out.println("####### time_out scattato #######");

            if(!this.bestActions.isEmpty())
                this.publicResult = this.bestActions.get(rand.nextInt(this.bestActions.size()));


            System.out.println("WELAAA Selected: {" + this.publicResult.toString() + "}");

            return this.publicResult;

        }catch (Exception e) {
            e.printStackTrace();
        }


        return this.publicResult;
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

    @Override
    public MyAction call() throws Exception {
        metrics = new Metrics();
        List<MyAction> actions = game.getActions(this.currState);
        Collections.shuffle(actions);
        this.publicResult = actions.get(0);
        this.bestActions.add(actions.get(0));

        int depth = this.depth;
        double resultValue = Double.NEGATIVE_INFINITY;
        State.Turn player = game.getPlayer(this.currState);
        for (MyAction action : actions) {
            double value = minValue(game.getResult(this.currState, action), player,
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
            this.publicResult = this.bestActions.get(rand.nextInt(this.bestActions.size()));
        }
        return this.publicResult;
    }
}