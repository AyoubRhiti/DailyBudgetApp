package com.example.budgingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticActivity extends AppCompatActivity {



    AnyChartView anyChartView;
    int transAmount = 0;
    int foodAmount = 0;
    int entAmount = 0;
    int otherAmount = 0;
    private List<Data> myDataList = new ArrayList<>();
    private ArrayAdapter<Data> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    public void setupPieChart(){
        Log.d("hello", "hi");
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("expenses").child(onlineUserId);
        adapter = new ArrayAdapter<Data>(this, R.layout.activity_statistic, myDataList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Data data = dataSnapshot.getValue(Data.class);
                    Log.d("hello", data.getItem());
                    if(data.getItem().equals("Food")){
                        foodAmount += data.getAmount();
                    } if (data.getItem().equals("Transport")){
                        transAmount += data.getAmount();
                    }if (data.getItem().equals("Entertainment")){
                        entAmount += data.getAmount();
                    }if (data.getItem().equals("Other")) {
                        otherAmount += data.getAmount();
                    }
                }


                dataEntries.add(new ValueDataEntry("Food", foodAmount));
                dataEntries.add(new ValueDataEntry("Transport", transAmount));
                dataEntries.add(new ValueDataEntry("Entertainment", entAmount));
                dataEntries.add(new ValueDataEntry("Other", otherAmount));
                pie.data(dataEntries);
                anyChartView.setChart(pie);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}