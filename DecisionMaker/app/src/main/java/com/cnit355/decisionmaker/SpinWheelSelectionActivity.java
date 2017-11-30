package com.cnit355.decisionmaker;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinWheelSelectionActivity extends AppCompatActivity {

    TableLayout tableLayout;
    int tableIndex;
    int rowID;
    int percID;
    int totalPercent;
    int titleID;
    Bundle data;
    ArrayList<Integer> Weights;
    ArrayList<String> Titles;
    DisplayMetrics displayMetrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel_selection);
        Weights = new ArrayList<Integer>();
        Titles = new ArrayList<String>();
        tableLayout = (TableLayout) findViewById(R.id.Table);
        tableIndex = 0;
        rowID = 0;
        percID = 1000;
        titleID = 2000;
        totalPercent = 0;
        AddSelection();
        AddSelection();

    }

    public void AddSelection()
    {
        final TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        row.setId(rowID);
        final EditText editText = new EditText(this);
        editText.setWidth(750);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().length() > 20)
                {
                    editText.setText(editText.getText().subSequence(0,20));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setId(titleID);
        titleID++;
        editText.setText("");
        editText.setHint("Type Piece Title");


        final EditText editText2 = new EditText(this);
        editText2.setText("");
        editText2.setWidth(200);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Integer.parseInt(editText2.getText().toString());
                }
                catch(Exception e) {
                    editText2.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int valid = Integer.parseInt(editText2.getText().toString());
                    if (valid < 0)
                    {
                        editText2.setText("0");
                        Toast.makeText(getBaseContext(), "Please enter something greater than 0", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e) {
                    editText2.setText("0");
                }
            }
        });
        editText2.setId(percID);
        percID++;

        editText2.setHint("Weight");

        final ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.drawable.redx);
        imageButton.setBackgroundColor(0);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               RemoveSelection(row.getId());
            }
        });
        row.addView(editText);
        row.addView(editText2);
        row.addView(imageButton);
        tableLayout.addView(row,tableIndex);
        tableIndex++;
        rowID++;
    }
    public void AddSelection(View View)
    {
        AddSelection();
    }

    public void RemoveSelection(int index)
    {
        tableLayout.removeView(findViewById(index));
        tableIndex--;
    }
    public void RecalculateTotalPercentage()
    {

        for (int x = 1000; x < percID; x++)
        {
            try {
                Log.d("Test", String.valueOf(x));
                EditText editText = (EditText) findViewById(x);
                totalPercent += Integer.parseInt(editText.getText().toString());
            }
            catch (Exception e)
            {
                Log.d("Test Fail", String.valueOf(x));
                x++;
            }
        }


    }

    public void Back (View view)
    {
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }

    public void Next (View view)
    {
        Weights.clear();
        Titles.clear();
        for (int x = 1000; x < percID; x++)
        {
            try {
                EditText editText = (EditText) findViewById(Integer.valueOf(x));
                int weight = Integer.parseInt(editText.getText().toString());
                Weights.add(weight);

            }
            catch (Exception e)
            {

            }

        }
        for (int x = 2000; x < titleID; x++)
        {
            try {
                EditText editText = (EditText) findViewById(Integer.valueOf(x));
                Titles.add(editText.getText().toString());
            }
            catch (Exception e)
            {

            }
        }

        Intent mIntent = new Intent(this, SpinWheelActivity.class);
        mIntent.removeExtra("Weight");
        mIntent.removeExtra("Title");
        mIntent.putExtra("Weight",Weights);
        mIntent.putExtra("Title", Titles);
        startActivity(mIntent);
    }
}
