package com.calvinbarker.squareswitcher;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Board board;
    Button[] buttons;

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
                        switchH, switchI, switchJ};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        board = new Board();
        setBoard();
        setMoveCount();
        setSequence();

    }

    public void setSquares(int[] squares) {
        ColorStateList offWhite = getResources().getColorStateList(R.color.off_white);
        ColorStateList darkGray = getResources().getColorStateList(R.color.dark_gray);
        for (int i = 0; i < 16; i++) {
            if (squares[i] == -1) {
                buttons[i].setBackgroundTintList(darkGray);
                //buttons[i].setTextColor(offWhite);
                //buttons[i].setBackgroundColor(getResources().getColor(R.color.colorDarkGray));
            }
            else
                buttons[i].setBackgroundTintList(offWhite);
                //buttons[i].setTextColor(darkGray);
                //buttons[i].setBackgroundColor(getResources().getColor(R.color.colorBackWhite));
        }
    }

    public void setBoard() {

        Button s1 = (Button) findViewById(R.id.sq0);
        Button s2 = (Button) findViewById(R.id.sq1);
        Button s3 = (Button) findViewById(R.id.sq2);
        Button s4 = (Button) findViewById(R.id.sq3);
        Button s5 = (Button) findViewById(R.id.sq4);
        Button s6 = (Button) findViewById(R.id.sq5);

        buttons = new Button[] {
                (Button) findViewById(R.id.sq0),  (Button) findViewById(R.id.sq1),
                (Button) findViewById(R.id.sq2),  (Button) findViewById(R.id.sq3),
                (Button) findViewById(R.id.sq4),  (Button) findViewById(R.id.sq5),
                (Button) findViewById(R.id.sq6),  (Button) findViewById(R.id.sq7),
                (Button) findViewById(R.id.sq8),  (Button) findViewById(R.id.sq9),
                (Button) findViewById(R.id.sq10), (Button) findViewById(R.id.sq11),
                (Button) findViewById(R.id.sq12), (Button) findViewById(R.id.sq13),
                (Button) findViewById(R.id.sq14), (Button) findViewById(R.id.sq15)
        };

        int[] squares = board.getAssignments();

        setSquares(squares);
    }

    public void resetBoard(View v) {
        board = new Board();
        setMoveCount();
        setSequence();
        setBoard();
    }

    public void setMoveCount() {
        TextView move_count = (TextView) findViewById(R.id.move_count);
        move_count.setText("Move Count\n" + board.getMoveCount());
    }

    public void setSequence() {
        TextView sequence = (TextView) findViewById(R.id.sequence);
        sequence.setText("Sequence: " + board.getSequence());
    }

    public void pressSwitch(View v) {
        switch (v.getId()) {
            case (R.id.swA):
                board.logMove("A");
                board.passSwitch(switchA);
                break;
            case (R.id.swB):
                board.logMove("B");
                board.passSwitch(switchB);
                break;
            case (R.id.swC):
                board.logMove("C");
                board.passSwitch(switchC);
                break;
            case (R.id.swD):
                board.logMove("D");
                board.passSwitch(switchD);
                break;
            case (R.id.swE):
                board.logMove("E");
                board.passSwitch(switchE);
                break;
            case (R.id.swF):
                board.logMove("F");
                board.passSwitch(switchF);
                break;
            case (R.id.swG):
                board.logMove("G");
                board.passSwitch(switchG);
                break;
            case (R.id.swH):
                board.logMove("H");
                board.passSwitch(switchH);
                break;
            case (R.id.swI):
                board.logMove("I");
                board.passSwitch(switchI);
                break;
            case (R.id.swJ):
                board.logMove("J");
                board.passSwitch(switchJ);
                break;
        }
        setMoveCount();
        setSquares(board.getAssignments());
        setSequence();
    }

    public void pressSolution(View v) {
        System.out.println("pressed");
        board.processSolution();
        setMoveCount();
        setSquares(board.getAssignments());
        setSequence();

    }
}
