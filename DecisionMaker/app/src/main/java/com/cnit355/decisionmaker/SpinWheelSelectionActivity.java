package com.cnit355.decisionmaker;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SpinWheelSelectionActivity extends AppCompatActivity {

    TableLayout tableLayout;
    int tableIndex;
    int rowID;
    int percID;
    int totalPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel_selection);
        tableLayout = (TableLayout) findViewById(R.id.Table);
        tableIndex = 0;
        rowID = 0;
        percID = 1000;
        totalPercent = 0;
        AddSelection();
        AddSelection();
    }

    public void AddSelection()
    {
        final TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setId(rowID);
        final EditText editText = new EditText(this);
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

        //editText.setId("txtPercentage" + String.valueOf(TableIndex));
        editText.setText("");
        editText.setHint("Type Piece Title");
        final EditText editText2 = new EditText(this);
        editText2.setId(percID);
        percID++;
        editText2.setText("");
        editText2.setHint("Percentage");
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
                    if (valid > 100 || valid < 0)
                    {
                        editText2.setText("0");
                        Toast.makeText(getBaseContext(), "Percentage must be greater than 0 and less than 100", Toast.LENGTH_LONG).show();
                    }
                    RecalculateTotalPercentage();
                }
                catch(Exception e) {
                    editText2.setText("0");
                }
            }
        });
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
        /*
        for (int x = 1000; x < percID; x++)
        {
            try {
                EditText editText = (EditText) findViewById(x);
                totalPercent += Integer.parseInt(editText.getText().toString());
            }
            catch (Exception e)
            {

            }

        }
        TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setText(totalPercent);
        */
    }
}
