package com.calvinbarker.squareswitcher;

/**
 * Created by barkerc1 on 2/7/17.
 */

public class Switches {

    /***********************************************************************/

    /**
     * Switches work by multiplying board values by 1 or -1 to change color
     */

    static final int[] switch0 = {1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,};

    static final int[] switchA = {-1, -1, -1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    static final int[] switchB = {1, 1, 1, -1,
            1, 1, 1, -1,
            1, -1, 1, -1,
            1, 1, 1, 1};
    static final int[] switchC = {1, 1, 1, 1,
            -1, 1, 1, 1,
            1, 1, -1, 1,
            1, 1, -1, -1};
    static final int[] switchD = {-1, 1, 1, 1,
            -1, -1, -1, -1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    static final int[] switchE = {1, 1, 1, 1,
            1, 1, -1, -1,
            -1, 1, -1, 1,
            -1, 1, 1, 1};
    static final int[] switchF = {-1, 1, -1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    static final int[] switchG = {1, 1, 1, -1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    static final int[] switchH = {1, 1, 1, 1,
            -1, -1, 1, -1,
            1, 1, 1, 1,
            1, 1, -1, -1};
    static final int[] switchI = {1, -1, -1, -1,
            -1, -1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1};
    static final int[] switchJ = {1, 1, 1, -1,
            -1, -1, 1, 1,
            1, -1, 1, 1,
            1, -1, 1, 1};

    static final int[][] switches = {switch0, switchA, switchB, switchC, switchD, switchE, switchF, switchG,
            switchH, switchI, switchJ, switch0};
    static final int[] idArray = {0, R.id.swA, R.id.swB, R.id.swC, R.id.swD, R.id.swE,
            R.id.swF, R.id.swG, R.id.swH, R.id.swI, R.id.swJ};

    static final String[] switchNames = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", ""};

    /***********************************************************************/
}
