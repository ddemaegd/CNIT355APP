package com.cnit355.decisionmaker;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.IntProperty;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SpinWheelActivity extends AppCompatActivity {

    PieChart pieChart ;
    FrameLayout frame;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    ArrayList<Integer> Weights;
    ArrayList<String> Titles;
    Random random;
    int spinLength;
    boolean isSpinning;

    float angle;
    float pivotX;
    float pivotY;
    Matrix matrix;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);

        Intent intent = getIntent();
        Weights = intent.getIntegerArrayListExtra("Weight");
        Titles = intent.getStringArrayListExtra("Title");

        pieChart = (PieChart) findViewById(R.id.chart1);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();
        AddValuesToPIEENTRY();
        AddValuesToPieEntryLabels();
        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.setRotationEnabled(false);

        random = new Random();
        angle = 0;
        isSpinning = false;
        imageView = (ImageView) findViewById(R.id.imageView4);


        FrameLayout layout = (FrameLayout) findViewById(R.id.framepointer);

        layout.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    // implement the onTouch() callback method
                        public boolean onTouch(View v, MotionEvent m) {
                            Log.d("YES", "YES");
                            if (!isSpinning)
                                Thread();
                            isSpinning = true;
                            return true;
                    }
                });

    }

    public void AddValuesToPIEENTRY(){
        int x = 0;
        for (Integer weight : Weights)
        {
            Log.d("Weight", String.valueOf(weight));
            entries.add(new BarEntry(weight, x));
            x++;
        }
       /*
        entries.add(new BarEntry(4f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(8f, 3));
        entries.add(new BarEntry(7f, 4));
        entries.add(new BarEntry(3f, 5));
        */
    }

    public void AddValuesToPieEntryLabels(){

        for (String title : Titles)
        {
            Log.d("Title", title);
            PieEntryLabels.add(title);
        }

        /*
        PieEntryLabels.add("February");
        PieEntryLabels.add("March");
        PieEntryLabels.add("April");
        PieEntryLabels.add("May");
        PieEntryLabels.add("June");
        */

    }


    public void Thread(){
        Runnable task = new Runnable(){
            public void run(){
                spinLength = random.nextInt(1700 - 700) + 700;
                while (spinLength > 0)
                {
                    try {
                        Thread.sleep(10); //Needs to sleep to update Thread
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    h.sendEmptyMessage(0); //Basically used for UI update
                    angle += spinLength / 180;
                    spinLength--;
                }
                isSpinning = false;

            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
    public void RotateAnimation(float x) throws InterruptedException {
        imageView.setRotation(x);
    }

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                try {
                    RotateAnimation(angle); //UI Update
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else{

            }
        }
    };

    public void Back (View view)
    {
        Intent mIntent = new Intent(this, SpinWheelSelectionActivity.class);
        startActivity(mIntent);
    }


}


