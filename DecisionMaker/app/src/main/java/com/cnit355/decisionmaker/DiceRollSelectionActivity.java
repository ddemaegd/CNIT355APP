package com.cnit355.decisionmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiceRollSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll_selection);
    }

    public void btnD4Click(View view) {
        moveToDieRoller(4);
    }

    public void btnD6Click(View view) {
        moveToDieRoller(6);
    }

    public void btnD8Click(View view) {
        moveToDieRoller(8);
    }

    public void btnD10Click(View view) {
        moveToDieRoller(10);
    }

    public void btnD12Click(View view) {
        moveToDieRoller(12);
    }

    public void btnBack(View view) {
        this.finish();
    }

    private void moveToDieRoller(int die) {
        Intent mIntent = new Intent(this, DiceRollActivity.class);
        mIntent.putExtra("die", die);
        startActivity(mIntent);
    }
}
