package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
/*
public class filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

public class filter extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private filteradapter adapter;
    private ArrayList<filterdata> list, list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        recyclerView = findViewById(R.id.rvf);

        //String jeerank = getIntent().getStringExtra("Rank");
         int req = 101;
        //show(Integer.parseInt(jeerank));
          show(req);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter2(newText);
                return false;
            }
        });
        return true;
    }

    private void filter2(String text) {
        ArrayList<filterdata> filteredlist = new ArrayList<>();

        for (filterdata item : list) {
            if (item.getText().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    private void show(int req) {

        list = new ArrayList<>();
        list2 = new ArrayList<>();


        list.add(new filterdata("SMVDU chemical", "20"));
        list.add(new filterdata("SMVDU civil", "25"));
        list.add(new filterdata("SMVDU mechanical", "30"));
        list.add(new filterdata("SMVDU Metallurgy", "35"));
        list.add(new filterdata("SMVDU Information", "40"));
        list.add(new filterdata("SMVDU Computer", "50"));
        list.add(new filterdata("SMVDU Production", "60"));
        list.add(new filterdata("SMVDU Aerospace", "65"));
        list.add(new filterdata("SMVDU Ceramics", "90"));
        list.add(new filterdata("SMVDU Electrical", "100"));
        list.add(new filterdata("SMVDU chemical", "20"));
        list.add(new filterdata("SMVDU civil", "25"));
        list.add(new filterdata("SMVDU mechanical", "30"));
        list.add(new filterdata("SMVDU Metallurgy", "35"));
        list.add(new filterdata("SMVDU Information", "40"));
        list.add(new filterdata("SMVDU Computer", "50"));
        list.add(new filterdata("SMVDU Production", "60"));
        list.add(new filterdata("SMVDU Aerospace", "65"));
        list.add(new filterdata("SMVDU Ceramics", "90"));
        list.add(new filterdata("SMVDU Electrical", "100"));


        int n = list.size();

        for (int i = 0; i < n; i++) {
            filterdata data = list.get(i);
            if (Integer.parseInt(data.getText2()) >= req) {
                list2.add(data);
            }
        }

        if (list2.isEmpty()) {
            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            SearchView searchView = findViewById(R.id.searchView);
            
        }
        adapter = new filteradapter(list2, this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.setDrawingCacheEnabled(true);
        //recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //recyclerView.setItemViewCacheSize(list.size());


    }
}