package com.calvinbarker.squareswitcher;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    Board board;
    Button[] buttons;


    Map<Integer, Integer[]> buttonMap = new HashMap<Integer, Integer[]>();

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
        Drawable darkButton = getResources().getDrawable(R.drawable.dark_button);
        Drawable lightButton = getResources().getDrawable(R.drawable.light_button);
        for (int i = 0; i < 16; i++) {
            if (squares[i] == -1)
                buttons[i].setBackground(darkButton);
            else
                buttons[i].setBackground(lightButton);
        }

        if(board.isSolved()){
            Toast toast = Toast.makeText(getApplicationContext(), "You won!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
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
                board.passSwitch(Switches.switchA);
                break;
            case (R.id.swB):
                board.logMove("B");
                board.passSwitch(Switches.switchB);
                break;
            case (R.id.swC):
                board.logMove("C");
                board.passSwitch(Switches.switchC);
                break;
            case (R.id.swD):
                board.logMove("D");
                board.passSwitch(Switches.switchD);
                break;
            case (R.id.swE):
                board.logMove("E");
                board.passSwitch(Switches.switchE);
                break;
            case (R.id.swF):
                board.logMove("F");
                board.passSwitch(Switches.switchF);
                break;
            case (R.id.swG):
                board.logMove("G");
                board.passSwitch(Switches.switchG);
                break;
            case (R.id.swH):
                board.logMove("H");
                board.passSwitch(Switches.switchH);
                break;
            case (R.id.swI):
                board.logMove("I");
                board.passSwitch(Switches.switchI);
                break;
            case (R.id.swJ):
                board.logMove("J");
                board.passSwitch(Switches.switchJ);
                break;
        }
        setMoveCount();
        setSquares(board.getAssignments());
        setSequence();
    }


    public void pressSolution(View v) {
        System.out.println("pressed");
        String sol = board.processSolution();

        System.out.println(sol);

        for (int i = 0; i < sol.length(); i++) {

            String move = String.valueOf(sol.charAt(i));
            System.out.println("Move: " + move);
            board.logMove(move);

            int[] moveSwitch = board.switchMap.get(move);
            board.passSwitch(moveSwitch);
            int buttonId = getButtonId(moveSwitch);

            System.out.println("Button id: " + buttonId);

            setMoveCount();
            setSquares(board.getAssignments());
            setSequence();
        }
    }

    private int getButtonId(int[] swt) {
        for (int i = 0; i < Switches.switches.length; i++) {
            if (Arrays.equals(Switches.switches[i], swt)) {
                return Switches.idArray[i];
            }
        }
        return -1;
    }
}
