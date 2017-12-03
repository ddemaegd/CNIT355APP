package com.cnit355.decisionmaker;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRollActivity extends AppCompatActivity {

    int dieSize;
    boolean blnAnimate;
    TextView txtOutput;
    List<ImageView> dieSides;
    Thread rollingThread;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        // Instantiate objects, and get the user inputs
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        Intent intent = getIntent();
        dieSize = intent.getIntExtra("die", 0);
        blnAnimate = intent.getBooleanExtra("animate", true);

        // Create the imageviews needed to show the die on all sides
        setupImageViews(dieSize);

        // Instantiate handler to animate die roll
        setupHandler();

        // Instantiate rollingThread
        rollingThread = new Thread(new rollingRunnable(dieSize, blnAnimate, handler));

    }

    private void setupHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // Retrieve data
                Bundle bnd = msg.getData();
                boolean isResult = bnd.getBoolean("isResult");
                int value = bnd.getInt("value");

                if(!isResult) {
                    txtOutput.setText("Rolling...");
                } else {
                    txtOutput.setText("Result: " + Integer.toString(value));
                }
                setDieImage(value);
            }
        };
    }

    private void setupImageViews(int dieSize) {
        // Find the layout for adding ImageViews
        RelativeLayout dieLayout = (RelativeLayout) findViewById(R.id.dieLayout);
        // Instantiate list of imageviews, for later use by animating functions
        dieSides = new ArrayList<ImageView>();
        // Get a scale and set the imageviews to 100 x 100dp
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int size = (int) (100 * scale + 0.5f);
        // Iterate through the size of teh die and setup imageviews
        for(int i=0; i < dieSize; i++) {
            // Get a new viewID to avoid duplicates
            Integer newID = View.generateViewId();

            // Setup the imageView
            ImageView thisImage = new ImageView(this);
            thisImage.setId(newID);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            thisImage.setLayoutParams(layoutParams);
            // Set the ImageResource depending on the die size
            switch (dieSize) {
                case 6:
                    thisImage.setImageResource(getD6Image(i + 1));
                    break;
            }
            if(i < dieSize - 1) {
                thisImage.setVisibility(View.INVISIBLE);
            } else {
                thisImage.setVisibility(View.VISIBLE);
            }
            dieSides.add(thisImage);
            dieLayout.addView(thisImage);
        }
    }

    public void btnBack(View view) {
        this.finish();
    }

    public void btnRoll(View view) {
        if(!rollingThread.isAlive()) {
            rollingThread.start();
        }
    }






    private void setDieImage(int result) {
        for(int i = 0; i < dieSize; i++) {
            ImageView thisImage = dieSides.get(i);
            thisImage.setVisibility(View.INVISIBLE);
        }
        ImageView resultFace = dieSides.get(result - 1);
        resultFace.setVisibility(View.VISIBLE);
        Random rand = new Random();
        resultFace.setRotation(rand.nextInt(360));
    }

    private int getD6Image(int side) {
        switch(side) {
            case 1:
                return R.drawable.d6_1;
            case 2:
                return R.drawable.d6_2;
            case 3:
                return R.drawable.d6_3;
            case 4:
                return R.drawable.d6_4;
            case 5:
                return R.drawable.d6_5;
            case 6:
                return R.drawable.d6_6;
            default:
                return -1;
        }
    }

}

class rollingRunnable implements Runnable {
    int dieSize;
    boolean blnAnimate;
    Handler handler;

    public rollingRunnable(int dieSize, boolean blnAnimate, Handler handler) {
        this.dieSize = dieSize;
        this.handler = handler;
        this.blnAnimate = blnAnimate;
    }

    @Override
    public void run() {
        try {
            animateDie();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(int value, boolean isResult) {
        Bundle bnd = new Bundle();
        bnd.putInt("value", value);
        bnd.putBoolean("isResult", isResult);
        Message msg = new Message();
        msg.setData(bnd);
        handler.sendMessage(msg);
    }

    private void animateDie() throws InterruptedException {
        final int waitTime = 200;
        if(blnAnimate) {
            Log.d("animateDie", "Animating Started");
            sendMessage(getRandom(dieSize), false);
            Thread.sleep(waitTime);
            sendMessage(getRandom(dieSize), false);
            Thread.sleep(waitTime);
            sendMessage(getRandom(dieSize), false);
            Thread.sleep(waitTime);
            sendMessage(getRandom(dieSize), false);
            Thread.sleep(2 * waitTime);
            sendMessage(getRandom(dieSize), false);
            Thread.sleep(3 * waitTime);
        }
        sendMessage(getRandom(dieSize), true);
    }

    private int getRandom(int dieSize) {
        Random rand = new Random();
        return rand.nextInt(dieSize)+1;
    }


}