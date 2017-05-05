package com.example.isas.rockpsgameai;
//
//Artificial intelligence rock, paper, scissors game.
//
//get tutorial online at
//https://www.youtube.com/watch?v=lSgyFO7xoh0
//

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Rockpsaimain extends ActionBarActivity {

    ////////////////////
    //Probability weight
    private double paperChance = 33;
    private double rockChance = 33;
    //scissors will be the
    //substraction of the previous two

    ///////////////////
    //App Thinking
    private double repeatStyleWin = 1;
    private double repeatStyleLose = 1;
    private double repeatStyleDraw = 1;
    private double alterStyleWin = 0;
    private double alterStyleLose = 0;
    private double alterStyleDraw = 0;
    private double rockCount = 0;
    private double paperCount = 0;
    private double scissorCount = 0;

    ///////////////////
    //App personality
    private double confidence = 5;
    private double forgetLimit = 3;

    //////////////////
    //game
    private int random;
    private String userChoice;
    private String aiChoice;
    private String result;
    private double aiScore = 0;
    private double userScore = 0;

    ///////////////////
    //App memory
    private String historyChoice = "none";
    private double historyResult;

    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rockpsaimain);
    }

    public void rock(View v) {
        /////////////////
        //learning start
        rockCount = rockCount + 1;          //counts rock
        userChoice = "rock";
        //////////////////

        //////////////////
        //luck starts
        random = r.nextInt(102 - 1);        //generate random numb
        /////////////////

        if (random < paperChance) {   //adjusts weights

            //////////////////
            //Game consequences start
            aiChoice = "paper";           //if ai chose paper
            aiScore = aiScore + 1;          //score goes up
            confidence = confidence + 1;    //confidence goes up
            result = "You Lost";
            //////////////////

            /////////////////////
            //Strategy Development start
            historyResult = 0;
            if (historyChoice.equals(userChoice)) {
                repeatStyleLose = repeatStyleLose + 1;
            } else {
                alterStyleLose = alterStyleLose + 1;
            }
            /////////////////////////


        } else {
            if (random < (paperChance + rockChance)) {
                aiChoice = "rock";
                result = "Draw";
                if (historyChoice.equals(userChoice)) {
                    repeatStyleDraw = repeatStyleDraw + 1;
                } else {
                    alterStyleDraw = alterStyleDraw + 1;
                }

            } else {
                aiChoice = "scissors";
                userScore = userScore + 1;
                confidence = confidence - 2;
                result = "You Won!";

                historyResult = 1;

                if (historyChoice.equals(userChoice)) {
                    repeatStyleWin = repeatStyleWin + 1;
                } else {
                    alterStyleWin = alterStyleWin + 1;
                }
            }
        }
        ///////////////////////
        //Strategy adjustment start
        if (result.equals("You Lost")) {
            if (alterStyleLose > repeatStyleLose) {
                rockChance = rockChance - (confidence / 2);
                paperChance = paperChance - confidence;
            } else if
                    (repeatStyleLose > alterStyleLose) {
                rockChance = rockChance + (confidence / 2);
                paperChance = paperChance + confidence;
            }
        } else {
            if (result.equals("You Won!")) {
                if (repeatStyleWin > alterStyleWin) {
                    rockChance = rockChance + (confidence / 2);
                    paperChance = paperChance + confidence;
                } else if
                        (alterStyleWin > repeatStyleWin) {
                    rockChance = rockChance - (confidence / 2);
                    paperChance = paperChance - confidence;
                }
            }
        }

        /////////////////////////////////////////////////////
        //diagnosis start (display how the AI is operating)
        ((TextView)findViewById(R.id.txtScore)).setText("Home: "+userScore+ " Visitor AI: " + aiScore);
        ((TextView)findViewById(R.id.txtResult)).setText(result+" "+ " AI's choice was "+aiChoice);

        ((TextView) findViewById(R.id.txtScissors)).setText("" + (100 - paperChance - rockChance));
        ((TextView) findViewById(R.id.txtPaper)).setText("" + paperChance);
        ((TextView) findViewById(R.id.txtRock)).setText("" + rockChance);

        ////////////////////////////////////////////////////
        //make the AI forget after a certain amount of moves
        //so there isn't a repeat glitch to win
        if (alterStyleWin > repeatStyleWin + forgetLimit) {
            alterStyleWin = alterStyleWin - 2;
        }
        if (repeatStyleWin > alterStyleWin + forgetLimit) {
            repeatStyleWin = repeatStyleWin - 2;
        }
        if (alterStyleDraw > repeatStyleDraw + forgetLimit) {
            alterStyleDraw = alterStyleDraw - 2;
        }
        if (repeatStyleDraw > alterStyleDraw + forgetLimit) {
            repeatStyleDraw = repeatStyleDraw - 2;
        }
        if (alterStyleLose > repeatStyleLose + forgetLimit) {
            alterStyleLose = alterStyleLose - 2;
        }
        if (repeatStyleLose > alterStyleLose + forgetLimit) {
            repeatStyleLose = repeatStyleLose - 2;
        }
        /////////////////////////////////////
        //keep the game "random"
        if (paperChance > 70) {
            paperChance = 50;
        }
        if (rockChance > 70) {
            rockChance = 50;
        }
        if (paperChance < 10) {
            paperChance = 15;
        }
        if (rockChance < 10) {
            rockChance = 15;
        }
        /////////////////////////////////
        //keep control on the confidence
        //so it doesn't drop
        //into the negatives
        if (confidence < 1) {
            confidence = 2;
        }
        /////////////////////////////////////////////////
        ((TextView) findViewById(R.id.txtRepeatWin)).setText("Repeated Win " + repeatStyleWin);
        ((TextView) findViewById(R.id.txtRepeatLose)).setText("Repeated Lost " + repeatStyleLose);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Win " + alterStyleWin);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Lost " + alterStyleLose);
        ////////////////////////////////////////////////

        historyChoice = "rock";
    }


/////////////////////////////////////////////////////

    public void paper(View v) {

        /////////////////
        //learning start
        ////////////////
        rockCount = paperCount + 1;          //counts rock
        //textview txtPaper
        userChoice = "paper";
        //////////////////

        //////////////////
        //luck starts
        /////////////////
        random = r.nextInt(102 - 1);        //generate random numb
        /////////////////

        if (random < paperChance) {   //adjusts weights

            //////////////////
            //Game consequences start
            /////////////////
            aiChoice = "paper";           //if ai chose paper
            result = "Draw";
            //////////////////

            /////////////////////
            //Strategy Development start
            ////////////////////

            if (historyChoice.equals(userChoice)) {
                repeatStyleDraw = repeatStyleDraw + 1;
            } else {
                alterStyleDraw = alterStyleDraw + 1;
            }
            /////////////////////////


        } else {
            if (random < (paperChance + rockChance)) {
                aiChoice = "rock";
                userScore = userScore + 1;
                result = "You Won!";

                historyResult = 1;

                if (historyChoice.equals(userChoice)) {
                    repeatStyleWin = repeatStyleWin + 1;
                } else {
                    alterStyleWin = alterStyleWin + 1;
                }

            } else {
                aiChoice = "scissors";
                confidence = confidence + 1;
                aiScore = aiScore + 1;
                result = "You Lost.";

                historyResult = 0;

                if (historyChoice.equals(userChoice)) {
                    repeatStyleLose = repeatStyleLose + 1;
                } else {
                    alterStyleLose = alterStyleLose + 1;
                }
            }
        }
        ///////////////////////
        //Strategy adjustment start
        ///////////////////////
        if (result.equals("You Lost")) {
            if (alterStyleLose > repeatStyleLose) {
                rockChance = rockChance + confidence;
                paperChance = paperChance + (confidence / 2);
            } else if
                    (repeatStyleLose > alterStyleLose) {
                rockChance = rockChance - confidence;
                paperChance = paperChance - (confidence / 2);
            }
        } else {
            if (result.equals("You Won!")) {
                if (repeatStyleWin > alterStyleWin) {
                    rockChance = rockChance - confidence;
                    ;
                    paperChance = paperChance - (confidence / 2);
                } else if
                        (alterStyleWin > repeatStyleWin) {
                    rockChance = rockChance + -confidence;
                    paperChance = paperChance + (confidence / 2);
                }
            }
        }
        /////////////////////////////////////////////////////
        //diagnosis start (display how the AI is operating)
        ////////////////////////////////////////////////
        ((TextView)findViewById(R.id.txtScore)).setText("Home: "+userScore+ " Visitor AI: " + aiScore);
        ((TextView)findViewById(R.id.txtResult)).setText(result+" "+ " AI's choice was "+aiChoice);

        ((TextView)findViewById(R.id.txtScissors)).setText(""+(100-paperChance-rockChance));
        ((TextView)findViewById(R.id.txtPaper)).setText(""+paperChance);
        ((TextView)findViewById(R.id.txtRock)).setText(""+rockChance);

        ////////////////////////////////////////////////////
        //make the AI forget after a certain amount of moves
        //so there isn't a repeat glitch to win
        if(alterStyleWin>repeatStyleWin+forgetLimit){
            alterStyleWin=alterStyleWin-2;
        }
        if(repeatStyleWin>alterStyleWin+forgetLimit){
            repeatStyleWin=repeatStyleWin-2;
        }
        if(alterStyleDraw>repeatStyleDraw+forgetLimit){
            alterStyleDraw=alterStyleDraw-2;
        }
        if(repeatStyleDraw>alterStyleDraw+forgetLimit){
            repeatStyleDraw=repeatStyleDraw-2;
        }
        if(alterStyleLose>repeatStyleLose+forgetLimit){
            alterStyleLose=alterStyleLose-2;
        }
        if(repeatStyleLose>alterStyleLose+forgetLimit){
            repeatStyleLose=repeatStyleLose-2;
        }
        /////////////////////////////////////
        //keep the game "random"
        if(paperChance>70){
            paperChance=50;
        }
        if(rockChance>70){
            rockChance=50;
        }
        if(paperChance<10){
            paperChance=15;
        }
        if(rockChance<10){
            rockChance=15;
        }
        /////////////////////////////////
        //keep control on the confidence
        //so it doesn't drop
        //into the negatives
        if(confidence<1){
            confidence=2;
        }
        /////////////////////////////////////////////////
        ((TextView) findViewById(R.id.txtRepeatWin)).setText("Repeated Win " + repeatStyleWin);
        ((TextView) findViewById(R.id.txtRepeatLose)).setText("Repeated Lost " + repeatStyleLose);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Win " + alterStyleWin);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Lost " + alterStyleLose);
        ////////////////////////////////////////////////

        historyChoice="paper";
    }

    //////////////////////////////////////////////
    public void scissors(View v)
    {
        scissorCount=scissorCount+1;
        userChoice = "scissors";
        //////////////////

        //////////////////
        //luck starts
        /////////////////
        random = r.nextInt(102 - 1);        //generate random numb
        /////////////////

        if (random < paperChance) {   //adjusts weights

            //////////////////
            //Game consequences start
            /////////////////
            aiChoice = "paper";           //if ai chose paper
            userScore = userScore + 1;
            result = "You Won!";

            historyResult = 1;

            if (historyChoice.equals(userChoice)) {
                repeatStyleWin = repeatStyleWin + 1;
            } else {
                alterStyleWin = alterStyleWin + 1;
            }

        } else {
            if (random < (paperChance + rockChance)) {
                aiChoice = "rock";
                aiScore = aiScore + 1;          //score goes up
                confidence = confidence + 1;    //confidence goes up
                result = "You Lost";
                //////////////////

                /////////////////////
                //Strategy Development start
                ////////////////////
                historyResult = 0;
                if (historyChoice.equals(userChoice)) {
                    repeatStyleLose = repeatStyleLose + 1;
                } else {
                    alterStyleLose = alterStyleLose + 1;
                }
                /////////////////////////

            } else {
                aiChoice = "scissors";
                result = "Draw";
                if (historyChoice.equals(userChoice)) {
                    repeatStyleDraw = repeatStyleDraw + 1;
                } else {
                    alterStyleDraw = alterStyleDraw + 1;
                }
            }
        }
        ///////////////////////
        //Strategy adjustment start
        ///////////////////////
        if (result.equals("You Lost")) {
            if (alterStyleLose > repeatStyleLose) {
                rockChance = rockChance - (confidence*(2/3));
                paperChance = paperChance + (confidence*(2/3));
            } else if
                    (repeatStyleLose > alterStyleLose) {
                rockChance = rockChance + (confidence*(2/3));
                paperChance = paperChance - (confidence*(2/3));
            }
        } else {
            if (result.equals("You Won!")) {
                if (repeatStyleWin > alterStyleWin) {
                    rockChance = rockChance + (confidence*(2/3));
                    paperChance = paperChance - (confidence*(2/3));
                } else if
                        (alterStyleWin > repeatStyleWin) {
                    rockChance = rockChance - (confidence*(2/3));
                    paperChance = paperChance + (confidence*(2/3));
                }
            }
        }

        /////////////////////////////////////////////////////
        //diagnosis start (display how the AI is operating)
        ///////////////////////////////////////////////
        ((TextView)findViewById(R.id.txtScore)).setText("Home: "+userScore+ " Visitor AI: " + aiScore);
        ((TextView)findViewById(R.id.txtResult)).setText(result+" "+ " AI's choice was "+aiChoice);

        ((TextView) findViewById(R.id.txtScissors)).setText("" + (100 - paperChance - rockChance));
        ((TextView) findViewById(R.id.txtPaper)).setText("" + paperChance);
        ((TextView) findViewById(R.id.txtRock)).setText("" + rockChance);

        ////////////////////////////////////////////////////
        //make the AI forget after a certain amount of moves
        //so there isn't a repeat glitch to win
        if (alterStyleWin > repeatStyleWin + forgetLimit) {
            alterStyleWin = alterStyleWin - 2;
        }
        if (repeatStyleWin > alterStyleWin + forgetLimit) {
            repeatStyleWin = repeatStyleWin - 2;
        }
        if (alterStyleDraw > repeatStyleDraw + forgetLimit) {
            alterStyleDraw = alterStyleDraw - 2;
        }
        if (repeatStyleDraw > alterStyleDraw + forgetLimit) {
            repeatStyleDraw = repeatStyleDraw - 2;
        }
        if (alterStyleLose > repeatStyleLose + forgetLimit) {
            alterStyleLose = alterStyleLose - 2;
        }
        if (repeatStyleLose > alterStyleLose + forgetLimit) {
            repeatStyleLose = repeatStyleLose - 2;
        }
        /////////////////////////////////////
        //keep the game "random"
        if (paperChance > 70) {
            paperChance = 50;
        }
        if (rockChance > 70) {
            rockChance = 50;
        }
        if (paperChance < 10) {
            paperChance = 15;
        }
        if (rockChance < 10) {
            rockChance = 15;
        }
        /////////////////////////////////
        //keep control on the confidence
        //so it doesn't drop
        //into the negatives
        if (confidence < 1) {
            confidence = 2;
        }
        /////////////////////////////////////////////////
        ((TextView) findViewById(R.id.txtRepeatWin)).setText("Repeated Win " + repeatStyleWin);
        ((TextView) findViewById(R.id.txtRepeatLose)).setText("Repeated Lost " + repeatStyleLose);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Win " + alterStyleWin);
        ((TextView) findViewById(R.id.txtAlterWin)).setText("Alternated Lost " + alterStyleLose);
        ////////////////////////////////////////////////

        historyChoice = "scissors";
    }

    /////////////////////////////////////////////////
    public void show(View v)
    {
        paperChance=33;
        rockChance=33;

        ((TextView)findViewById(R.id.txtScissors)).setText(""+(100-paperChance-rockChance));
        ((TextView)findViewById(R.id.txtPaper)).setText(""+(100+paperChance));
        ((TextView)findViewById(R.id.txtRock)).setText(""+(100+rockChance));
    }
    ////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //inflate the menu; this adds items to the action
        //bar if it is present
        getMenuInflater().inflate(R.menu.aimenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.credits:
            startActivity(new Intent(this, credits.class));
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    ////////////////////////////////////////////////////

}