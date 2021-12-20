package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    TextView texte;
    private String message;
    private static final String Tag = "MainActivity";
    private ArrayList<DayList> mButtons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getButtons();
    }
    private void getButtons() {
        Log.d(Tag, "onCreateViewHolder: called.");
        DayList menu1 = new DayList("lundi");
        DayList menu2 = new DayList("mardi");
        DayList menu3 = new DayList("mercredi");
        DayList menu4 = new DayList("jeudi");
        DayList menu5 = new DayList("vendredi");
        mButtons.add(menu1);
        mButtons.add(menu2);
        mButtons.add(menu3);
        mButtons.add(menu4);
        mButtons.add(menu5);
        initRecyclerView();
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        Adapter adapter=new Adapter(mButtons, this, new Adapter.ItemClickListner() {
            @Override
            public void onItemClick(DayList dayList) {


            }
        });
        recyclerView.setAdapter(adapter);
    }
}