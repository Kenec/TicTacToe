package com.dev.kene.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button three_by_three, five_by_five;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);

        three_by_three = (Button) findViewById(R.id.three_board);
        five_by_five = (Button) findViewById(R.id.five_board);

    }

    /**
     *
     * @param {View} - the button views
     */
    public void onClick(View v) {
        Intent startGame;
        switch (v.getId()) {
            case R.id.three_board:
                startGame = new Intent(this, StartGame.class);
                startGame.putExtra("board", "three");
                startActivity(startGame);
                break;
            
            case R.id.five_board:
                startGame = new Intent(this, StartGame.class);
                startGame.putExtra("board", "five");
                startActivity(startGame);
                break;
        }
    }
}
