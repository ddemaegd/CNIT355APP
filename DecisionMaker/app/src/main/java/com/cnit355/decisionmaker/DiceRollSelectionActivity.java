package com.cnit355.decisionmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiceRollSelectionActivity extends AppCompatActivity {

    boolean blnAnimate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll_selection);
    }

    public void chkAnimateClick(View view) {
        blnAnimate = !blnAnimate;
    }

    public void btnD6Click(View view) {
        Intent mIntent = new Intent(this, DiceRollActivity.class);
        mIntent.putExtra("die", 6);
        mIntent.putExtra("animate", blnAnimate);
        startActivity(mIntent);
    }

    public void btnBack(View view) {
        this.finish();
    }

}
