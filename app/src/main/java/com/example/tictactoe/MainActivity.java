package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.util.Log;
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

    private Button reset, playagain, rules;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
            {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    int rounds;

    TextView textView;
    Button resetButton;

    private ImageButton[] imageBackup;
    private int playerOneScoreCount, playerTwoScoreCount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = findViewById(R.id.score_Player1);
        playerTwoScore = findViewById(R.id.score_Player2);
        reset = findViewById(R.id.btn_reset);
        playagain = findViewById(R.id.btn_play_again);
        rules = findViewById(R.id.btn_rules);
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
        rules.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Create and configure the alert dialog
                ShowRulesAlert();
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        if(checkWinner())
        {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));
        String backgroundImageName = (String) view.getTag();

        for(int i=0; i<buttons.length; i++)
        {
            if (buttons[i].getTag() == backgroundImageName)
            {
                Log.e("SomeBug", String.valueOf(buttons[i]));
                buttons[i].setOnClickListener(null);
            }
        }

        if(gameStatePointer != 0 || gameStatePointer != 1)
        {
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
        }

        if(checkWinner())
        {
            if(playerOneActive)
            {
                playerOneScoreCount++;
                updatePlayerScore();
//                playerStatus.setText("Samsung has won");
                ShowWinnerAlert("Samsung");
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
//                playerStatus.setText("Apple has won");
                ShowWinnerAlert("Apple");
            }
        }
        else if(rounds==9)
        {
//            playerStatus.setText("No Winner");
            ShowWinnerAlert("Friendship");
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

        playagain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                playAgain();
                for(int i=0; i<buttons.length; i++)
                {
                    buttons[i].setBackgroundResource(R.drawable.custom_button);
                }
            }
        });

        rules.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Create and configure the alert dialog
                ShowRulesAlert();
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
            buttons[i].setOnClickListener(this);
        }
    }

    private void ShowWinnerAlert(String winner)
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

    private void ShowRulesAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Rules")
            .setMessage("There will be some rules. ...")
            .setPositiveButton("Let's go!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // You can add code here to handle the OK button click.
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