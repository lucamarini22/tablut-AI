package it.unibo.ai.didattica.competition.tablut.client;

import it.unibo.ai.didattica.competition.tablut.domain.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

public class MarenTablutAIClient extends TablutClient {

    static private final int REPEATED_MOVES_ALLOWED = 99;
    static private final int CACHE_SIZE = 0;
    static private final String LOGS_FOLDER = "garbage";
    static private final String W_B_NAME = "fake";




    public MarenTablutAIClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
    }

    @Override
    public void run() {
        try {
            this.declareName();
        } catch (Exception ignored) {
        }

        State state;
        Game rules = null;
        state = new StateTablut();
        state.setTurn(State.Turn.WHITE);
        rules = new GameAshtonTablut(REPEATED_MOVES_ALLOWED, CACHE_SIZE, LOGS_FOLDER, W_B_NAME, W_B_NAME);
        System.out.println("You are player " + this.getPlayer().toString() + "!");

        while (true) {
            try {
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                System.exit(1);
            }
            state = this.getCurrentState();
            System.out.println("Current state:");
            System.out.println(state.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            if (this.getPlayer().equals(State.Turn.WHITE)) {
                if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
                    // TO-DO

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
                if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
                    // TO-DO

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
        String ipAddress = "localhost";
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
