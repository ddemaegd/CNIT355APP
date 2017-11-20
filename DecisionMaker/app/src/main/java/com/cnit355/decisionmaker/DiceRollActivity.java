package com.cnit355.decisionmaker;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DiceRollActivity extends AppCompatActivity {

    int dieSize;
    ImageView diePicture;
    TextView txtOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diePicture = (ImageView) findViewById(R.id.imgDie);
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        Intent intent = getIntent();
        dieSize = intent.getIntExtra("die", 0);

        if(dieSize == 6) {
            diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_6, null));
        }

    }

    public void btnBack(View view) {
        this.finish();
    }

    public void btnRoll(View view) {
        int result = getResult(dieSize);
        try {
            animateDie(dieSize);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(dieSize == 6) {
            setD6Image(result);
            txtOutput.setText("Result: " + Integer.toString(result));
        }
    }

    private int getResult(int dieSize) {
        Random rand = new Random();
        return rand.nextInt(dieSize)+1;
    }

    private void setD6Image(int result) {
        switch(result) {
            case 1:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_1, null));
                break;
            case 2:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_2, null));
                break;
            case 3:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_3, null));
                break;
            case 4:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_4, null));
                break;
            case 5:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_5, null));
                break;
            case 6:
                diePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.d6_6, null));
                break;
        }
    }

    private void animateDie(int dieSize) throws InterruptedException {

        setDieImage(dieSize, getResult(dieSize));
        Thread.sleep(150);
        setDieImage(dieSize, getResult(dieSize));
        Thread.sleep(200);
        setDieImage(dieSize, getResult(dieSize));
        Thread.sleep(300);
        setDieImage(dieSize, getResult(dieSize));
        Thread.sleep(450);
        setDieImage(dieSize, getResult(dieSize));

    }

    private void setDieImage(int dieSize, int result) {
        switch(dieSize) {
            case 6:
                setD6Image(result);
                break;
        }
    }


}
