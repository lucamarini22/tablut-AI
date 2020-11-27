package it.unibo.ai.didattica.competition.tablut.maren.client;

import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.maren.game.*;
import it.unibo.ai.didattica.competition.tablut.maren.logic.AlphaBetaSearch;
import java.io.IOException;
import java.net.UnknownHostException;

public class MarenTablutAIClient extends TablutClient {
    private static final int DEPTH = 4;

    private final AlphaBetaSearch alphaBetaSearch;

    public MarenTablutAIClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
        MyGame<MyState, MyAction, State.Turn> game = new GameImpl(4);
        this.alphaBetaSearch = new AlphaBetaSearch(game, DEPTH, timeout);
    }

    @Override
    public void run() {
        try {
            this.declareName();
        } catch (Exception ignored) {
        }
        State state;
        state = new StateTablut();
        state.setTurn(State.Turn.WHITE);
        System.out.println("You are player " + this.getPlayer().toString() + "!");

        while (true) {
            try {
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                System.exit(1);
            }
            state = this.getCurrentState();
            MyState myState = new MyStateImpl(DEPTH).updateState(state);
            System.out.println("Current state:");
            System.out.println(state.toString());
            myState.printBoard();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            if (this.getPlayer().equals(State.Turn.WHITE)) {
                if (state.getTurn().equals(State.Turn.WHITE)) {
                    Action a = null;
                    MyAction a2 = this.alphaBetaSearch.makeDecision(myState);
                    String from = state.getBox(a2.getRowFrom(), a2.getColumnFrom());
                    String to = state.getBox(a2.getRowTo(), a2.getColumnTo());
                    try {
                        a = new Action(from, to, StateTablut.Turn.WHITE);
                    } catch (IOException ignored) {
                    }
                    System.out.println("Mossa scelta: " + a.toString());
                    try {
                        this.write(a);
                    } catch (ClassNotFoundException | IOException ignored) {
                    }
                } else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
                    System.out.println("Waiting for your opponent move... ");
                } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }
            } else {
                if (state.getTurn().equals(State.Turn.BLACK)) {
                    Action a = null;
                    MyAction a2 = this.alphaBetaSearch.makeDecision(myState);
                    String from = state.getBox(a2.getRowFrom(), a2.getColumnFrom());
                    String to = state.getBox(a2.getRowTo(), a2.getColumnTo());
                    try {
                        a = new Action(from, to, StateTablut.Turn.BLACK);
                    } catch (IOException ignored) {
                    }
                    System.out.println("Mossa scelta: " + a.toString());
                    try {
                        this.write(a);
                    } catch (ClassNotFoundException | IOException ignored) {
                    }
                } else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
                    System.out.println("Waiting for your opponent move... ");
                } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        String role = "";
        String name = "MarenTablutAI";
        String ipAddress = "";
        int timeout = 0;

        if (args.length < 3) {
            System.out.println("You must specify in order: which player you are (WHITE or BLACK), your name, the timeout and the IP address of the server");
            System.exit(-1);
        } else if (args.length == 3){
            role = (args[0]);
            timeout = Integer.parseInt(args[1]);
            ipAddress = args[2];
            System.out.println("Selected client: " + args[0]);
        } else {
            System.out.println(args.length);
            System.out.println("You specified too many parameters");
            System.exit(-1);
        }
        MarenTablutAIClient client = new MarenTablutAIClient(role, name, timeout, ipAddress);
        client.run();
    }
}