package com.cnit355.decisionmaker;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tx = (TextView)findViewById(R.id.txtTitle);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/DAGGERSQUARE.ttf");

        tx.setTypeface(custom_font);
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
