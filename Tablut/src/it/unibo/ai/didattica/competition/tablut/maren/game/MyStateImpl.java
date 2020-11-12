package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyStateImpl implements MyState {

    private final Board board;
    private State.Turn turn;
    private int currentDepth;

    public MyStateImpl(int depth) {
        this.setTurn(State.Turn.WHITE);
        this.board = new BoardImpl();
        this.setCurrentDepth(depth);
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }


    @Override
    public void updateState(State currentState) {
        this.board.setBoard(currentState.getBoard());
        this.setTurn(currentState.getTurn());
    }


    @Override
    public State.Turn getTurn() {
        return this.turn;
    }

    @Override
    public void setTurn(State.Turn turn) {
        this.turn = turn;
    }


    @Override
    public List<MyAction> getPossibleActions() {
        /* get white and black pos from boardImpl.
           Then for each pos get all horizontal (right and left) and vertical (up and down) cells,
           and for each cell
                try to understand if the white pawn or the black pawn considered can move into that cell (check if it's a
                special area (for the special area use getSquareType from BoardImpl) or if there's another pawn.
                    If it can move there, then add the move into the List of MyAction that has to be returned,
                    if not, do not add (and if not, because there's another pawn, then stop to go to that direction)
         */

        List<MyAction> allPossibleActions = new ArrayList<>();
        List<Pair<Integer, Integer>> whitePositions = this.board.getWhitePositions();
        List<Pair<Integer, Integer>> blackPositions = this.board.getBlackPositions();

        if (this.getTurn() == State.Turn.WHITE) {
            // Get all possible white actions
            whitePositions.forEach(wp -> {

                // Get all possible horizontal left actions for white Pawns
                List<Pair<Integer, Integer>> horLeftCells = this.board.getHorizontalLeftCells(wp.getFirst(), wp.getSecond());
                for (Pair<Integer, Integer> hor_l_c : horLeftCells) {
                    // If there's a Pawn, break and consider the next white Pawn
                    if (this.board.isThereAPawn(hor_l_c.getFirst(), hor_l_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle and it is not a camp
                    if ((!this.board.isCastle(hor_l_c.getFirst(), hor_l_c.getSecond()))
                            && (!this.board.isCamp(hor_l_c.getFirst(), hor_l_c.getSecond()))) {
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                                this.board.fromIntToLetter(hor_l_c.getSecond()) + (hor_l_c.getFirst() + 1), this.getTurn()));

                    }
                }


                // Get all possible horizontal actions
                List<Pair<Integer, Integer>> horRightCells = this.board.getHorizontalRightCells(wp.getFirst(), wp.getSecond());
                for (Pair<Integer, Integer> hor_r_c : horRightCells) {
                    // If there's a Pawn, break and consider the next white Pawn
                    if (this.board.isThereAPawn(hor_r_c.getFirst(), hor_r_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle and it is not a camp
                    if ((!this.board.isCastle(hor_r_c.getFirst(), hor_r_c.getSecond()))
                            && (!this.board.isCamp(hor_r_c.getFirst(), hor_r_c.getSecond()))) {
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                                this.board.fromIntToLetter(hor_r_c.getSecond()) + (hor_r_c.getFirst() + 1), this.getTurn()));

                    }
                }

                List<Pair<Integer, Integer>> vertUpCells = this.board.getVerticalUpCells(wp.getFirst(), wp.getSecond());
                for (Pair<Integer, Integer> vert_up_c : vertUpCells) {
                    // If there's a Pawn, break and consider the next white Pawn
                    if (this.board.isThereAPawn(vert_up_c.getFirst(), vert_up_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle and it is not a camp
                    if ((!this.board.isCastle(vert_up_c.getFirst(), vert_up_c.getSecond()))
                            && (!this.board.isCamp(vert_up_c.getFirst(), vert_up_c.getSecond()))) {
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                                this.board.fromIntToLetter(vert_up_c.getSecond()) + (vert_up_c.getFirst() + 1), this.getTurn()));

                    }
                }


                List<Pair<Integer, Integer>> verDownCells = this.board.getVerticalDownCells(wp.getFirst(), wp.getSecond());
                for (Pair<Integer, Integer> vert_d_c : verDownCells) {
                    // If there's a Pawn, break and consider the next white Pawn
                    if (this.board.isThereAPawn(vert_d_c.getFirst(), vert_d_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle and it is not a camp
                    if ((!this.board.isCastle(vert_d_c.getFirst(), vert_d_c.getSecond()))
                            && (!this.board.isCamp(vert_d_c.getFirst(), vert_d_c.getSecond()))) {
                        allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(wp.getSecond()) + (wp.getFirst() + 1),
                                this.board.fromIntToLetter(vert_d_c.getSecond()) + (vert_d_c.getFirst() + 1), this.getTurn()));

                    }
                }

            });
        } else {
            // Get all possible black actions

            blackPositions.forEach(bp -> {

                // Get all possible horizontal left actions
                List<Pair<Integer, Integer>> horLeftCells = this.board.getHorizontalLeftCells(bp.getFirst(), bp.getSecond());
                for (Pair<Integer, Integer> hor_l_c : horLeftCells) {
                    // If there's a Pawn, break and consider the next black Pawn
                    if ( this.board.isThereAPawn(hor_l_c.getFirst(), hor_l_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle
                    if ( (! this.board.isCastle(hor_l_c.getFirst(), hor_l_c.getSecond()))) {

                        if (this.board.isCamp(hor_l_c.getFirst(), hor_l_c.getSecond())) {
                            // If in the cell is a camp, check whether the black Pawn was already outside the Camp or not
                            if (this.board.isCamp(bp.getFirst(), bp.getSecond())) {
                                // If the black was already in the camp, then he can move into the camp
                                allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                        this.board.fromIntToLetter(hor_l_c.getSecond()) + (hor_l_c.getFirst() + 1), this.getTurn()));
                            }

                        } else {
                            // If in the cell is a camp, then I can move into it (I add it into the possible actions)
                            allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                    this.board.fromIntToLetter(hor_l_c.getSecond()) + (hor_l_c.getFirst() + 1), this.getTurn()));
                        }
                    }
                }


                // Get all possible horizontal right actions
                List<Pair<Integer, Integer>> horRightCells = this.board.getHorizontalRightCells(bp.getFirst(), bp.getSecond());
                for (Pair<Integer, Integer> hor_r_c : horRightCells) {
                    // If there's a Pawn, break and consider the next black Pawn
                    if ( this.board.isThereAPawn(hor_r_c.getFirst(), hor_r_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle
                    if ( (! this.board.isCastle(hor_r_c.getFirst(), hor_r_c.getSecond()))) {

                        if (this.board.isCamp(hor_r_c.getFirst(), hor_r_c.getSecond())) {
                            // If in the cell is a camp, check whether the black Pawn was already outside the Camp or not
                            if (this.board.isCamp(bp.getFirst(), bp.getSecond())) {
                                // If the black was already in the camp, then he can move into the camp
                                allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                        this.board.fromIntToLetter(hor_r_c.getSecond()) + (hor_r_c.getFirst() + 1), this.getTurn()));
                            }

                        } else {
                            // If in the cell is a camp, then I can move into it (I add it into the possible actions)
                            allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                    this.board.fromIntToLetter(hor_r_c.getSecond()) + (hor_r_c.getFirst() + 1), this.getTurn()));
                        }
                    }
                }


                // Get all possible vertical up actions
                List<Pair<Integer, Integer>> vertUpCells = this.board.getVerticalUpCells(bp.getFirst(), bp.getSecond());
                for (Pair<Integer, Integer> vert_up_c : vertUpCells) {
                    // If there's a Pawn, break and consider the next black Pawn
                    if ( this.board.isThereAPawn(vert_up_c.getFirst(), vert_up_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle
                    if ( (! this.board.isCastle(vert_up_c.getFirst(), vert_up_c.getSecond()))) {

                        if (this.board.isCamp(vert_up_c.getFirst(), vert_up_c.getSecond())) {
                            // If in the cell is a camp, check whether the black Pawn was already outside the Camp or not
                            if (this.board.isCamp(bp.getFirst(), bp.getSecond())) {
                                // If the black was already in the camp, then he can move into the camp
                                allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                        this.board.fromIntToLetter(vert_up_c.getSecond()) + (vert_up_c.getFirst() + 1), this.getTurn()));
                            }

                        } else {
                            // If in the cell is a camp, then I can move into it (I add it into the possible actions)
                            allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                    this.board.fromIntToLetter(vert_up_c.getSecond()) + (vert_up_c.getFirst() + 1), this.getTurn()));
                        }
                    }
                }


                // Get all possible vertical up actions
                List<Pair<Integer, Integer>> vertDownCells = this.board.getVerticalDownCells(bp.getFirst(), bp.getSecond());
                for (Pair<Integer, Integer> vert_d_c : vertDownCells) {
                    // If there's a Pawn, break and consider the next black Pawn
                    if ( this.board.isThereAPawn(vert_d_c.getFirst(), vert_d_c.getSecond())) {
                        break;
                    }
                    // If the cell is not a castle
                    if ( (! this.board.isCastle(vert_d_c.getFirst(), vert_d_c.getSecond()))) {

                        if (this.board.isCamp(vert_d_c.getFirst(), vert_d_c.getSecond())) {
                            // If in the cell is a camp, check whether the black Pawn was already outside the Camp or not
                            if (this.board.isCamp(bp.getFirst(), bp.getSecond())) {
                                // If the black was already in the camp, then he can move into the camp
                                allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                        this.board.fromIntToLetter(vert_d_c.getSecond()) + (vert_d_c.getFirst() + 1), this.getTurn()));
                            }

                        } else {
                            // If in the cell is a camp, then I can move into it (I add it into the possible actions)
                            allPossibleActions.add(new MyActionImpl(this.board.fromIntToLetter(bp.getSecond()) + (bp.getFirst() + 1),
                                    this.board.fromIntToLetter(vert_d_c.getSecond()) + (vert_d_c.getFirst() + 1), this.getTurn()));
                        }
                    }
                }



            });

        }








        allPossibleActions.forEach(System.out::println);
        System.out.println(allPossibleActions.size());


        /*
        List l = new LinkedList<>();
        MyAction a = new MyActionImpl("e3", "f3", this.getTurn());
        MyAction b = new MyActionImpl("e4", "f4", this.getTurn());
        l.add(a);
        l.add(b);*/
        return allPossibleActions;
    }

    @Override
    public int getCurrentDepth() {
        return this.currentDepth;
    }
}
