package com.calvinbarker.squareswitcher;

import java.util.HashMap;

/**
 * Created by barkerc1 on 1/27/17.
 */

public class Board {

    private int[] assignments;
    private int move_count;
    private String sequence;
    protected HashMap<String, int[]> switchMap = new HashMap<>();


    /***********************************************************************/


    /**
     * Constructor
     */

    public Board() {
        resetBoard();
    }





    /***********************************************************************/

    /**
     * The following are board configurations
     */

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
    public int[] getBlankBoard() {
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
     * @return void a board that can be beaten with some combination of switches so that the game
     * can actually be fun
     */
    public void setBeatableBoard() {
        assignments = getBlankBoard();
        for (int i = 0; i < Switches.switches.length-1; i++){
            if (Math.random() < 0.5) {
                passSwitch(Switches.switches[i]);
            }
        }
    }

    /***********************************************************************/




    /***********************************************************************/

    /**
     * Getters
     */

    /**
     *
     * @return board assignments (-1 or 1)
     */
    public int[] getAssignments(){
        return assignments;
    }


    /**
     *
     * @return total moves
     */
    public int getMoveCount() {
        return move_count;
    }

    /**
     *
     * @return a string of switches pressed
     */
    public String getSequence() {
        return sequence;
    }

    /***********************************************************************/




    /***********************************************************************/

    /**
     * Board utility functions
     *
     */

    protected void resetBoard() {
        //setBeatableBoard();
        assignments = getBlankBoard();
        move_count = 0;
        sequence = "";
    }

    protected void passSwitch(int[] swt) {
        move_count++;
        for (int i = 0; i < 16; i++) {
            assignments[i] *= swt[i];
        }
    }

    protected void logMove(String c) {
        if (sequence.equals("")) {
            sequence = c;
        } else
            sequence += ", " + c;
    }

    /**
     * builds the switch map
     */
    protected void buildMap() {
        for (int i = 0; i < Switches.switchNames.length; i++) {
            switchMap.put(Switches.switchNames[i], Switches.switches[i]);
        }
    }

    /***********************************************************************/




    /***********************************************************************/

    /**
     * Automated solutions finder functions
     */

    /**
     *
     * @param squares - current board assignments
     * @param swt - switch
     * @return a board with the switch applied
     */
    private int[] solSwitch(int[] squares, int[] swt) {
        int[] newSquares = new int[16];
        for (int i = 0; i < 16; i++)
            newSquares[i] = squares[i] * swt[i];
        return newSquares;
    }

    /**
     * Check to see if the board has already been solved
     */
    public boolean isSolved(int[] squares) {
        int squareSums = 0;
        for (int i : squares)
            squareSums += i;
        if (squareSums == 16 || squareSums == -16)
            return true;
        return false;
    }

    public boolean isSolved(){
        return isSolved(assignments);
    }

    /**
     * Preps the board for the minimum solution
     * @return a string a solutions
     */
    public String processSolution() {
        buildMap();
        move_count = 0;
        sequence = "";
        return findSol(assignments,0);
    }

    /**
     * Works using a preorder, depth-first search of possible sequences
     */
    private String findSol(int[] squares, int switchPos) {
        // Returns blank string if no more switches are needed
        if (isSolved(squares)) {
            return "";
        } else {
            String currentSol  = "No solution";
            int [] tempSquares = solSwitch(squares, Switches.switches[switchPos]);

            for (int sw = switchPos + 1; sw < Switches.switches.length; sw++) {
                String move = findSol(tempSquares, sw);

                if (!move.equals("No solution")) {
                    String tempSol = Switches.switchNames[switchPos].concat(move);

                    if (tempSol.length() < currentSol.length()) {
                        currentSol = tempSol;
                    }
                }
            }
            return currentSol;
        }
    }


    /***********************************************************************/


}
