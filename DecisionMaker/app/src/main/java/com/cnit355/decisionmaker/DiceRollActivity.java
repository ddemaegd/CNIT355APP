package com.cnit355.decisionmaker;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRollActivity extends AppCompatActivity {

    int dieSize;
    boolean blnAnimate = true;
    TextView txtOutput;
    List<ImageView> dieSides;
    Thread thread;
    Handler handler;
    boolean blnCoinIsHeads = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        // Instantiate objects, and get the user inputs
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        Intent intent = getIntent();
        dieSize = intent.getIntExtra("die", 0);

        // Create the imageviews needed to show the die on all sides
        setupImageViews(dieSize);

        // Instantiate handler to animate die roll
        setupHandler();

        // Instantiate thread
        setupThread();

        // If this is a coin flip, change the checkbox and button texts
        if(dieSize == 2) {
            txtOutput.setText("Click Coin to Flip");
            CheckBox chkAnimate = (CheckBox) findViewById(R.id.chkAnimate);
            chkAnimate.setText("Animate Coin Flip");
        }

        RelativeLayout dieArea = (RelativeLayout) findViewById(R.id.dieLayout);
        dieArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!thread.isAlive()) {
                    thread.start();
                }
            }
        });

    }

    private void setupThread() {
        switch(dieSize) {
            case 2:
                thread = new Thread(new flippingRunnable(blnAnimate, blnCoinIsHeads, handler));
                break;
            default:
                thread = new Thread(new rollingRunnable(dieSize, blnAnimate, handler));
        }
    }

    private void setupHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(dieSize != 2) {
                    handleDieRoll(msg);
                } else {
                    handleCoinFlip(msg);
                }
            }

            private void handleCoinFlip(Message msg) {
                Bundle bnd = msg.getData();
                int rotation = bnd.getInt("rotation");
                boolean blnAnimatingHeads = bnd.getBoolean("heads");
                boolean isResult = bnd.getBoolean("isResult");
                setCoinImage(rotation, blnAnimatingHeads);
                if(rotation > -90 && rotation < 90) {
                    blnCoinIsHeads = blnAnimatingHeads;
                }
                if(isResult) {
                    if(blnAnimatingHeads) {
                        txtOutput.setText("Result: Heads");
                    } else {
                        txtOutput.setText("Result: Tails");
                    }
                } else {
                    txtOutput.setText("Flipping");
                }
            }

            private void handleDieRoll(Message msg) {
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
                case 2:
                    thisImage.setImageResource(getCoinImage(i + 1));
                    break;
                case 6:
                    thisImage.setImageResource(getD6Image(i + 1));
                    break;
            }
            if(i < dieSize - 1) {
                if(dieSize == 2) {
                    thisImage.setRotationY(-90);
                } else {
                    thisImage.setVisibility(View.INVISIBLE);
                }
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

    public void chkAnimateClick(View view) {
        blnAnimate = !blnAnimate;

        // A parameter for thread has changed, reinstantiate
        setupThread();
    }

    private void setCoinImage(int rotation, boolean isHeads) {
        ImageView side;
        if(isHeads) {
            side = dieSides.get(1);
        } else {
            side = dieSides.get(0);
        }
        side.setRotationY(rotation);
        if(rotation > -89 && rotation < 89) {
            side.bringToFront();
            side.setVisibility(View.VISIBLE);
        } else {
            side.setVisibility(View.INVISIBLE);
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

    private int getCoinImage(int side) {
        switch(side) {
            case 1:
                return R.drawable.tails;
            case 2:
                return R.drawable.heads;
            default:
                return -1;
        }
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
        if(blnAnimate) {
            try {
                animateDie();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sendMessage(getRandom(dieSize), true);
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

    private int getRandom(int dieSize) {
        Random rand = new Random();
        return rand.nextInt(dieSize)+1;
    }


}

class flippingRunnable implements Runnable {
    boolean blnAnimate;
    Handler handler;
    boolean blnIsHeads;
    final int delay = 10;
    final int numRotations = 2;

    public flippingRunnable(boolean blnAnimate, boolean blnIsHeads, Handler handler) {
        this.blnAnimate = blnAnimate;
        this.blnIsHeads = blnIsHeads;
        this.handler = handler;
    }

    @Override
    public void run() {
        int result = getRandom();
        if(blnAnimate) {
            for(int i = 0; i < numRotations; i++) {
                int startSpeed = (6 - (2 * i));
                fullRotate(startSpeed);
            }
            if(result == 1) {
                // Tails
                if(blnIsHeads) {
                    flipOver(2);
                }
                sendMessage(0, false, true);
            } else {
                // Heads
                if(!blnIsHeads) {
                    flipOver(2);
                }
                sendMessage(0, true, true);
            }
        } else {
            if(result == 1) {
                // Tails
                sendMessage(-90, true, false);
                sendMessage(0, false, true);
                blnIsHeads = false;
            } else {
                // Heads
                sendMessage(-90, false, false);
                sendMessage(0, true, true);
                blnIsHeads = true;
            }
        }
    }

    private void sendMessage(int rotation, boolean blnHeads, boolean isResult) {
        Bundle bnd = new Bundle();
        bnd.putInt("rotation", rotation);
        bnd.putBoolean("heads", blnHeads);
        bnd.putBoolean("isResult", isResult);
        Message msg = new Message();
        msg.setData(bnd);
        handler.sendMessage(msg);
    }

    private void fullRotate(int startSpeed) {
        flipOver(startSpeed);
        flipOver(startSpeed - 1);
    }

    private void flipOver(int speed) {
        if(blnIsHeads) {
            headsToTails(speed);
        } else {
            tailsToHeads(speed);
        }
        blnIsHeads = !blnIsHeads;
    }

    private void headsToTails(int speed) {
        headsOut(speed);
        tailsIn(speed);
    }

    private void tailsToHeads(int speed) {
        tailsOut(speed);
        headsIn(speed);
    }

    private void headsIn(int speed) {
        for(int i = -90; i <= 0; i += speed) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i > 0) {
                i = 0;
            }
            sendMessage(i, true, false);
        }
    }

    private void headsOut(int speed) {
        for(int i = 0; i <= 90; i += speed) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i > 90) {
                i = 90;
            }
            sendMessage(i, true, false);
        }
    }

    private void tailsIn(int speed) {
        for(int i = -90; i <= 0; i += speed) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i > 0) {
                i = 0;
            }
            sendMessage(i, false, false);
        }
    }

    private void tailsOut(int speed) {
        for(int i = 0; i <= 90; i += speed) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i > 90) {
                i = 90;
            }
            sendMessage(i, false, false);
        }
    }

    private int getRandom() {
        Random rand = new Random();
        return rand.nextInt(2)+1;
    }
}