package com.calvinbarker.squareswitcher;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by barkerc1 on 1/27/17.
 */

public class Board {

    private int[] assignments;
    private int move_count;
    private String sequence;

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

    public void resetBoard() {
        assignments = getRandomizedBoard();
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
     *
     * @return an array of 16 integers (all randomly-generated 1's or -1's to correspond with black/white)
     */
    public int[] getAssignments(){
        return assignments;
    }

    protected void findSolution(int[] squares) {

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
