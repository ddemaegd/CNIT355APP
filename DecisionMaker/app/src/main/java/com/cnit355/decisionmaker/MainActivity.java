package com.cnit355.decisionmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void MoveToCoinActivity (View view){
        Intent mIntent = new Intent(this, CoinFlipActivity.class);
        startActivity(mIntent);
    }

    public void MoveToDiceSelActivity (View view){
        Intent mIntent = new Intent(this, DiceRollSelectionActivity.class);
        startActivity(mIntent);
    }

    public void MoveToSpinSelActivity (View view){
        Intent mIntent = new Intent(this, SpinWheelSelectionActivity.class);
        startActivity(mIntent);
    }

}
