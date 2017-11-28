package com.cnit355.decisionmaker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.IntProperty;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.DonutChart;

public class SpinWheelActivity extends AppCompatActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);
        DonutChart donut = (DonutChart) findViewById(R.id.piegraph);
        ChartData values = new ChartData();
        values.setSectorValue(4);
        donut.addSector(values);
        values = new ChartData();
        values.setSectorValue(3);
        donut.addSector(values);
        values = new ChartData();
        values.setSectorValue(8);
        donut.addSector(values);
        values = new ChartData();
        values.setSectorValue(5);
        donut.addSector(values);
    }
    */
    PieChart pieChart ;
    FrameLayout frame;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    ArrayList<Integer> Weights;
    ArrayList<String> Titles;
    List<Integer> Colors;


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


}


