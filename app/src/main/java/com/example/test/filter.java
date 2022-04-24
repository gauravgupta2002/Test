package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class filter extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private filteradapter adapter;
    private ArrayList<filterdata> list, list2;
    Button button,button2,buttonpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        recyclerView = findViewById(R.id.rvf);

        //Intent intent = getIntent();
        //String str = intent.getStringExtra("message_key");
        int req = 20;
        show(req);

        button = (Button) findViewById(R.id.sort);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Collections.sort(list2, new Comparator<filterdata>() {
                    @Override
                    public int compare(filterdata n1, filterdata n2) {
                        //return n1.getText().compareTo(n2.getText());
                        return Integer.parseInt(n1.getText2())-Integer.parseInt(n2.getText2());
                    }
                });
                adapter.filterList(list2);
            }
        });

        button2 = (Button) findViewById(R.id.dcsort);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Collections.sort(list2, new Comparator<filterdata>() {
                    @Override
                    public int compare(filterdata n1, filterdata n2) {
                        //return n1.getText().compareTo(n2.getText());
                        return Integer.parseInt(n2.getText2())-Integer.parseInt(n1.getText2());
                    }
                });
                adapter.filterList(list2);
            }
        });


        buttonpdf = findViewById(R.id.pdf);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        createPdf(list2);

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

        for (filterdata item : list2) {
            if (item.getText().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
           // button = (Button) findViewById(R.id.sort);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Collections.sort(filteredlist, new Comparator<filterdata>() {
                        @Override
                        public int compare(filterdata n1, filterdata n2) {
                            //return n1.getText().compareTo(n2.getText());
                            return Integer.parseInt(n1.getText2())-Integer.parseInt(n2.getText2());
                        }
                    });
                    adapter.filterList(filteredlist);
                }
            });

           // button2 = (Button) findViewById(R.id.dcsort);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Collections.sort(filteredlist, new Comparator<filterdata>() {
                        @Override
                        public int compare(filterdata n1, filterdata n2) {
                            //return n1.getText().compareTo(n2.getText());
                            return Integer.parseInt(n2.getText2())-Integer.parseInt(n1.getText2());
                        }
                    });
                    adapter.filterList(filteredlist);
                }
            });

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
        list.add(new filterdata("SMVDU Electrical", "101"));
        list.add(new filterdata("SMVDU chemical", "21"));
        list.add(new filterdata("SMVDU civil", "28"));
        list.add(new filterdata("SMVDU mechanical", "33"));
        list.add(new filterdata("SMVDU Metallurgy", "39"));
        list.add(new filterdata("SMVDU Information", "44"));
        list.add(new filterdata("SMVDU Computer", "55"));
        list.add(new filterdata("SMVDU Production", "68"));
        list.add(new filterdata("SMVDU Aerospace", "670"));
        list.add(new filterdata("SMVDU Ceramics", "901"));
        list.add(new filterdata("SMVDU Electrical", "2000"));


        int n = list.size();

        for (int i = 0; i < n; i++) {
            filterdata data = list.get(i);
            //if (Integer.parseInt(data.getText2()) >= req) {
            if(Integer.parseInt(data.getText2()) >= req){
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

    }

    private void createPdf(ArrayList<filterdata> list2) {
        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(250,1000,1).create();
                PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

                Canvas canvas = myPage.getCanvas();
               // canvas.drawText("Welcome",50,50,paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(12.0f);
                canvas.drawText("JOSAA 2022",pageInfo.getPageWidth()/2,30,paint);

                paint.setTextSize(6.0f);
                canvas.drawText("IIT NIT IIIT CFTI",pageInfo.getPageWidth()/2,40,paint);

                paint.setTextSize(8.0f);
                canvas.drawText("College w.r.t. Last Year Closing Rank",pageInfo.getPageWidth()/2,60,paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(9.0f);


                int startX = 10, startY = 80, endPos = pageInfo.getPageWidth() - 10;
                for (int i = 0; i < list2.size(); i++) {
                    filterdata data = list2.get(i);
                    canvas.drawText(data.getText(), startX, startY, paint);
                    canvas.drawText(data.getText2(),startX+200,startY,paint);
                    startY += 15;
                }



                pdfDocument.finishPage(myPage);

                //File file = new File(Environment.getExternalStorageDirectory(),"/FirstPdf.pdf");
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/FirstPDF1.pdf");
                try{
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(filter.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
                }  catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
            }
        });
    }


    /* int startX = 10, startY = 20, endPos = pageInfo.getPageWidth() - 10;
          for (int i = 0; i < list2.size(); i++) {
             filterdata data = list2.get(i);
             canvas.drawText(data.getText(), startX, startY, paint);
             startY += 5;
         }*/


}