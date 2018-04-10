package com.dev.kene.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kenechukwunnamani on 10/04/2018.
 */

public class StartGame extends AppCompatActivity implements View.OnClickListener{

    private Button[][] gameBox;

    private boolean player1Turn = false;

    private int boardSize;
    private int roundCount = 0;
    private int player1Points = 0;
    private int player2points = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startGame = getIntent();
        String board;
        if(null != startGame){
            board = startGame.getStringExtra("board");
            if(board.equals("three")) {
                boardSize = 3;
                gameBox = new Button[3][3];
                setContentView(R.layout.game_3_by_3_layout);
                drawGameBoard(3);
            } else{
                boardSize = 5;
                gameBox = new Button[5][5];
                setContentView(R.layout.game_5_by_5_layout);
                drawGameBoard(5);
            }
        }

        textViewPlayer1 = findViewById(R.id.player1_score);
        textViewPlayer2 = findViewById(R.id.player2_score);

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void resetGame() {
        player1Points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard(boardSize);
    }

    private void drawGameBoard(int boardSize){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++ ) {
                String buttonID = "gameslot_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                gameBox[i][j] = findViewById(resID);
                gameBox[i][j].setOnClickListener(this);

            }
        }
    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if(player1Turn) {
            ((Button)v).setText("X");
        } else {
            ((Button)v).setText("O");
        }

        roundCount++;

        if(checkForWin(boardSize)){
            if(player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == (boardSize * boardSize)){
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin(int boardSize){
        String[][] field = new String[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++) {
                field[i][j] = gameBox[i][j].getText().toString();
            }
        }

        if (boardSize == 3) {
            for (int i = 0; i < 3; i++) {
                if (field[i][0].equals(field[i][1])
                        && field[i][0].equals(field[i][2])
                        && !field[i][0].equals("")) {
                    return true;
                }
            }

            for (int i = 0; i < 3; i++) {
                if (field[0][i].equals(field[1][i])
                        && field[0][i].equals(field[2][i])
                        && !field[0][i].equals("")) {
                    return true;
                }
            }

            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")) {
                return true;
            }

            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")) {
                return true;
            }
        } else if (boardSize == 5) {
            for (int i = 0; i < 5; i++) {
                if (field[i][0].equals(field[i][1])
                        && field[i][0].equals(field[i][2])
                        && field[i][0].equals(field[i][3])
                        && field[i][0].equals(field[i][4])
                        && !field[i][0].equals("")) {
                    return true;
                }
            }

            for (int i = 0; i < 5; i++) {
                if (field[0][i].equals(field[1][i])
                        && field[0][i].equals(field[2][i])
                        && field[0][i].equals(field[3][i])
                        && field[0][i].equals(field[4][i])
                        && !field[0][i].equals("")) {
                    return true;
                }
            }

            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && field[0][0].equals(field[3][3])
                    && field[0][0].equals(field[4][4])
                    && !field[0][0].equals("")) {
                return true;
            }

            if (field[0][4].equals(field[1][3])
                    && field[0][4].equals(field[2][2])
                    && field[0][4].equals(field[3][1])
                    && field[0][4].equals(field[4][0])
                    && !field[0][4].equals("")) {
                return true;
            }
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard(boardSize);
    }

    private void player2Wins(){
        player2points++;
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard(boardSize);
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetBoard(boardSize);
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2points);
    }

    private void resetBoard(int boardSize){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBox[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2points);
        outState.putBoolean("player1turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1turn");

    }
}
