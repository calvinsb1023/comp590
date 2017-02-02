package com.calvinbarker.squareswitcher;

import java.util.HashMap;
import java.util.Arrays;

/**
 * Created by barkerc1 on 1/27/17.
 */

public class Board {

    private int[] assignments;
    private int move_count;
    private String sequence;
    private HashMap<String, int[]> switchMap = new HashMap<>();

    int[] switch0 = {1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,};

    int[] switchA = {-1, -1, -1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    int[] switchB = {1, 1, 1, -1,
            1, 1, 1, -1,
            1, -1, 1, -1,
            1, 1, 1, 1};
    int[] switchC = {1, 1, 1, 1,
            -1, 1, 1, 1,
            1, 1, -1, 1,
            1, 1, -1, -1};
    int[] switchD = {-1, 1, 1, 1,
            -1, -1, -1, -1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    int[] switchE = {1, 1, 1, 1,
            1, 1, -1, -1,
            -1, 1, -1, 1,
            -1, 1, 1, 1};
    int[] switchF = {-1, 1, -1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    int[] switchG = {1, 1, 1, -1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    int[] switchH = {1, 1, 1, 1,
            -1, -1, 1, -1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    int[] switchI = {1, -1, -1, -1,
            -1, -1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    int[] switchJ = {1, 1, 1, -1,
            -1, -1, 1, 1,
            1, -1, 1, 1,
            1, -1, 1, 1};

    int[][] switches = {switch0, switchA, switchB, switchC, switchD, switchE, switchF, switchG,
            switchH, switchI, switchJ, switch0};

    String[] switchNames = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", ""};


    public Board() {
        resetBoard();
    }

    /**
     *
     * @return an integer array of size 16 with each index randomly assigned assignments -1 or 1
     */
    public int[] getRandomizedBoard() {
        int[] arr = new int[16];

        // -1 will correspond with black, 1 will correspond with white
        for (int i = 0; i < 16; i++) {
            if (Math.random() < 0.5)
                arr[i] = -1;
            else
                arr[i] = 1;
        }
        return arr;
    }

    /**
     *
     * @return blank board where all items are white or black
     */
    public int[] blankBoard() {
        if (Math.random() < 0.5) {
            int[] tB = {1, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1};
            return tB;
        }
        int[] tB = {-1, -1, -1, -1,
                -1, -1, -1, -1,
                -1, -1, -1, -1,
                -1, -1, -1, -1};
        return tB;
    }

    /**
     *
     * @return a board that can be beaten with some combination of switches so that the game
     * can actually be fun
     */
    public void setBeatableBoard() {
        assignments = blankBoard();
        for (int i = 0; i < switches.length; i++){
            if (Math.random() < 0.5) {
                passSwitch(switches[i]);
            }
        }
    }

    /**
     *
     * @return an array of 16 integers (all randomly-generated 1's or -1's to correspond with black/white)
     */
    public int[] getAssignments(){
        return assignments;
    }

    protected void resetBoard() {
        setBeatableBoard();
        move_count = 0;
        sequence = "";
    }

    protected void passSwitch(int[] swt) {
        move_count++;
        for (int i = 0; i < 16; i++) {
            assignments[i] *= swt[i];
        }
    }

    private int[] solSwitch(int[] squares, int[] swt) {
        int[] newSquares = new int[16];
        for (int i = 0; i < 16; i++)
            newSquares[i] = squares[i] * swt[i];
        return newSquares;
    }

    protected void logMove(String c) {
        if (sequence.equals("")) {
            sequence = c;
        } else
        sequence += ", " + c;
    }

    // Check to see if the puzzle has already been solved
    public boolean isSolved(int[] squares) {
        int squareSums = 0;
        for (int i : squares)
            squareSums += i;
        if (squareSums == 16 || squareSums == -16)
            return true;
        return false;
    }

    public void processSolution() {

        System.out.println(Arrays.toString(assignments));

        for (int i = 0; i < switchNames.length; i++) {
            switchMap.put(switchNames[i], switches[i]);
        }

        String sol = findSol(assignments, 0);

        if (sol.equals("Solution Not Found")) {
            System.out.println("No solution found");
            sequence = "Sorry, no solution found :/";
        } else {
            System.out.println("Solution:" + sol);
            move_count = 0;
            sequence = "";
            for (int i = 0; i < sol.length(); i++) {
                String sw = String.valueOf(sol.charAt(i));
                logMove(sw);
                passSwitch(switchMap.get(sw));
            }
        }
    }

    protected String findSol(int[] squares, int switchPos) {
        // Returns blank string if no more switches are needed
        if (isSolved(squares)) {
            return "";
        } else {
            String currentSol  = "Solution Not Found";
            int [] tempSquares = solSwitch(squares, switches[switchPos]);

            for (int sw = switchPos + 1; sw < switches.length; sw++) {
                String move = findSol(tempSquares, sw);

                if (!move.equals("Solution Not Found")) {
                    String tempSol = switchNames[switchPos].concat(move);

                    if (tempSol.length() < currentSol.length()) {
                        currentSol = tempSol;
                    }
                }
            }
            //System.out.println("out of for loop:" + currentSol);
            return currentSol;
        }
    }

    public int getMoveCount() {
        return move_count;
    }

    /**
     *
     * @return a list of minimal switches needed to solve the board
     */
    public String getSequence() {
        return sequence;
    }
}
