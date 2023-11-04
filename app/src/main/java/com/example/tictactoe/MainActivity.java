package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    boolean playerOneActive;
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private ImageButton[] buttons = new ImageButton[9];
    private Button reset, playagain;
    boolean isButtonPressed = false;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
            {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    int rounds;

    TextView textView;
    Button resetButton;
    private int playerOneScoreCount, playerTwoScoreCount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = findViewById(R.id.score_Player1);
        playerTwoScore = findViewById(R.id.score_Player2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playagain = findViewById(R.id.btn_play_again);
        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);
        for(int i=0; i<buttons.length; i++)
        {
            buttons[i].setOnClickListener(this);
        }
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        playerOneActive = true;
        rounds = 0;
    }

    @Override
    public void onClick(View view)
    {
//        if(view.drawable != null)
//        {
//            return;
//        }
        if(checkWinner())
        {
            return;
        }
        String buttonID  = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

//        ImageButton imageButton1 = findViewById(R.id.btn0);
//        int currentImageResource = (int) imageButton1.getTag();

//        if (!isButtonPressed);
//        {
            if(playerOneActive)
            {
                ((ImageButton)view).setBackgroundResource(R.drawable.samsung);
                gameState[gameStatePointer] = 0;
            }
            else
            {
                ((ImageButton)view).setBackgroundResource(R.drawable.apple);
                gameState[gameStatePointer] = 1;
            }
            rounds++;
            isButtonPressed = true;
//        }

        if(checkWinner())
        {
            if(playerOneActive)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Samsung has won");
                AlertFunction("Samsung");
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Apple has won");
                AlertFunction("Apple");
            }
        }
        else if(rounds==9)
        {
            playerStatus.setText("No Winner");
        }
        else
        {
            playerOneActive = !playerOneActive;
        }

        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                playAgain();
                playerOneScoreCount= 0;
                playerTwoScoreCount= 0;
                updatePlayerScore();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((ImageButton)view).setBackgroundResource(R.drawable.custom_button);
                playAgain();
             //   ((ImageButton)view).setBackgroundResource(R.drawable.custom_button);
                for(int i=0; i<buttons.length; i++)
                {
                    buttons[i].setBackgroundResource(R.drawable.custom_button);
                }
            }
        });
    }

    private boolean checkWinner()
    {
        boolean winnerResults  = false;
        for (int[] winningPositions: winningPositions)
        {
            if(gameState[winningPositions[0]]==gameState[winningPositions[1]]&&
                    gameState[winningPositions[1]]==gameState[winningPositions[2]] &&
                    gameState[winningPositions[0]]!=2)
            {
                winnerResults = true;
            }
        }
        return winnerResults;
    }

    private void playAgain()
    {
        rounds = 0;
        playerOneActive = true;
        for (int i=0; i<buttons.length; i++)
        {
            gameState[i] = 2;
        }
        playerStatus.setText("Status");
    }

    private void AlertFunction(String winner)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("We have winner")
                .setMessage(winner + " has won.")
                .setPositiveButton("Cool!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        // Show the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updatePlayerScore()
    {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
}