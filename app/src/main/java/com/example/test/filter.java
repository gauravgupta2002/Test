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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class filter extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private filteradapter adapter;
    private ArrayList<filterdata> list, list2;
    Button button, button2, buttonpdf, buttonexl;
    private  File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/FirstPDF1.xls");
    //private File filePath = new File(Environment.getExternalStorageDirectory() + "/Demo.xls");

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
                        return Integer.parseInt(n1.getText2()) - Integer.parseInt(n2.getText2());
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
                        return Integer.parseInt(n2.getText2()) - Integer.parseInt(n1.getText2());
                    }
                });
                adapter.filterList(list2);
            }
        });


        buttonpdf = findViewById(R.id.pdf);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        createPdf(list2);

        buttonexl = findViewById(R.id.excel);
        creatExcel(list2);

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
                            return Integer.parseInt(n1.getText2()) - Integer.parseInt(n2.getText2());
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
                            return Integer.parseInt(n2.getText2()) - Integer.parseInt(n1.getText2());
                        }
                    });
                    adapter.filterList(filteredlist);
                }
            });

            adapter.filterList(filteredlist);
        }

          createPdf(filteredlist);
    }

    private void show(int req) {

        list = new ArrayList<>();
        list2 = new ArrayList<>();


        /*list.add(new filterdata("SMVDU Mechanical with M.Tech in Manufacturing Engineering(5 Years,B.Tech/M.Tech(Dual Degree))", "20"));
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
        list.add(new filterdata("SMVDU Electrical", "2000"));*/

        list.add(new filterdata("NIT Jalandhar, Bio Technology (4 Years, B.Tech)","47423"));
        list.add(new filterdata("NIT Jalandhar, Chemical (4 Years, B.Tech)","36008"));
        list.add(new filterdata("NIT Jalandhar, Civil (4 Years, B.Tech)","38735"));
        list.add(new filterdata("NIT Jalandhar, Computer Science (4 Years, B.Tech)","9117"));
        list.add(new filterdata("NIT Jalandhar, Electrical (4 Years, B.Tech)","22288"));
        list.add(new filterdata("NIT Jalandhar, Electronics and Communication (4 Years, B.Tech)","14684"));
        list.add(new filterdata("NIT Jalandhar, Production (4 Years, B.Tech)","45696"));
        list.add(new filterdata("NIT Jalandhar, Information Technology (4 Years, B.Tech)","11138"));
        list.add(new filterdata("NIT Jalandhar, Instrumentation and Control (4 Years, B.Tech)","33079"));
        list.add(new filterdata("NIT Jalandhar, Mechanical (4 Years, B.Tech)","29582"));
        list.add(new filterdata("NIT Jalandhar, Textile Technology (4 Years, B.Tech)","50125"));
        list.add(new filterdata("NIT Jaipur, Architecture (5 Years, Bachelor of Architecture)","818"));
        list.add(new filterdata("NIT Jaipur, Chemical (4 Years, B.Tech)","24715"));
        list.add(new filterdata("NIT Jaipur, Civil (4 Years, B.Tech)","27936"));
        list.add(new filterdata("NIT Jaipur, Computer Science (4 Years, B.Tech)","3142"));
        list.add(new filterdata("NIT Jaipur, Electrical (4 Years, B.Tech)","14500"));
        list.add(new filterdata("NIT Jaipur, Electronics and Communication (4 Years, B.Tech)","9726"));
        list.add(new filterdata("NIT Jaipur, Mechanical (4 Years, B.Tech)","19453"));
        list.add(new filterdata("NIT Jaipur, Metallurgy (4 Years, B.Tech)","37506"));
        list.add(new filterdata("NIT Bhopal, Architecture (5 Years, Bachelor of Architecture)","971"));
        list.add(new filterdata("NIT Bhopal, Chemical (4 Years, B.Tech)","32372"));
        list.add(new filterdata("NIT Bhopal, Civil (4 Years, B.Tech)","35372"));
        list.add(new filterdata("NIT Bhopal, Computer Science (4 Years, B.Tech)","7244"));
        list.add(new filterdata("NIT Bhopal, Electrical (4 Years, B.Tech)","19898"));
        list.add(new filterdata("NIT Bhopal, Electronics and Communication (4 Years, B.Tech)","13845"));
        list.add(new filterdata("NIT Bhopal, Materials Science and Metallurgy (4 Years, B.Tech)","43020"));
        list.add(new filterdata("NIT Bhopal, Mathematics and Data Science (5 Years, B.Tech/M.Tech (Dual Degree))","14008"));
        list.add(new filterdata("NIT Bhopal, Mechanical (4 Years, B.Tech)","26985"));
        list.add(new filterdata("NIT Bhopal, Planning (4 Years, Bachelor of Planning)","726"));
        list.add(new filterdata("NIT Allahabad, Bio Technology (4 Years, B.Tech)","35018"));
        list.add(new filterdata("NIT Allahabad, Chemical (4 Years, B.Tech)","23040"));
        list.add(new filterdata("NIT Allahabad, Civil (4 Years, B.Tech)","27329"));
        list.add(new filterdata("NIT Allahabad, Computer Science (4 Years, B.Tech)","2751"));
        list.add(new filterdata("NIT Allahabad, Electrical (4 Years, B.Tech)","12648"));
        list.add(new filterdata("NIT Allahabad, Electronics and Communication (4 Years, B.Tech)","9238"));
        list.add(new filterdata("NIT Allahabad, Mechanical (4 Years, B.Tech)","18047"));
        list.add(new filterdata("NIT Allahabad, Production (4 Years, B.Tech)","30909"));
        list.add(new filterdata("NIT Agartala, Biotechnology and BioChemical (4 Years, B.Tech)","56064"));
        list.add(new filterdata("NIT Agartala, Chemical (4 Years, B.Tech)","51250"));
        list.add(new filterdata("NIT Agartala, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","58734"));
        list.add(new filterdata("NIT Agartala, Civil (4 Years, B.Tech)","52016"));
        list.add(new filterdata("NIT Agartala, Computational Mathematics (5 Years, B.Tech/M.Tech (Dual Degree))","36527"));
        list.add(new filterdata("NIT Agartala, Computer Science (4 Years, B.Tech)","19692"));
        list.add(new filterdata("NIT Agartala, Electrical (4 Years, B.Tech)","38398"));
        list.add(new filterdata("NIT Agartala, Electronics and Communication (4 Years, B.Tech)","28735"));
        list.add(new filterdata("NIT Agartala, Electronics and Instrumentation (4 Years, B.Tech)","40210"));
        list.add(new filterdata("NIT Agartala, Engineering Physics (5 Years, B.Tech/M.Tech (Dual Degree))","39911"));
        list.add(new filterdata("NIT Agartala, Mathematics & Computing (5 Years, B.Sc/M.Sc (Dual Degree))","36387"));
        list.add(new filterdata("NIT Agartala, Mechanical (4 Years, B.Tech)","42915"));
        list.add(new filterdata("NIT Agartala, Physics (5 Years, B.Sc/M.Sc (Dual Degree))","50451"));
        list.add(new filterdata("NIT Agartala, Production Engineering (4 Years, B.Tech)","57466"));
        list.add(new filterdata("NIT Calicut, Architecture (5 Years, Bachelor of Architecture)","451"));
        list.add(new filterdata("NIT Calicut, Bio Technology (4 Years, B.Tech)","37076"));
        list.add(new filterdata("NIT Calicut, Chemical (4 Years, B.Tech)","26508"));
        list.add(new filterdata("NIT Calicut, Civil (4 Years, B.Tech)","29982"));
        list.add(new filterdata("NIT Calicut, Computer Science (4 Years, B.Tech)","3436"));
        list.add(new filterdata("NIT Calicut, Electrical and Electronics (4 Years, B.Tech)","13520"));
        list.add(new filterdata("NIT Calicut, Electronics and Communication (4 Years, B.Tech)","8647"));
        list.add(new filterdata("NIT Calicut, Engineering Physics (4 Years, B.Tech)","19909"));
        list.add(new filterdata("NIT Calicut, Materials Science and Engineering (4 Years, B.Tech)","36626"));
        list.add(new filterdata("NIT Calicut, Mechanical (4 Years, B.Tech)","18621"));
        list.add(new filterdata("NIT Calicut, Production Engineering (4 Years, B.Tech)","31987"));
        list.add(new filterdata("NIT Delhi, Computer Science (4 Years, B.Tech)","7614"));
        list.add(new filterdata("NIT Delhi, Electrical and Electronics (4 Years, B.Tech)","19259"));
        list.add(new filterdata("NIT Delhi, Electronics and Communication (4 Years, B.Tech)","14427"));
        list.add(new filterdata("NIT Durgapur, Bio Technology (4 Years, B.Tech)","46557"));
        list.add(new filterdata("NIT Durgapur, Biotechnology (5 Years, B.Tech/M.Tech (Dual Degree))","47797"));
        list.add(new filterdata("NIT Durgapur, Chemical (4 Years, B.Tech)","35345"));
        list.add(new filterdata("NIT Durgapur, Chemical (5 Years, B.Tech/M.Tech (Dual Degree))","35841"));
        list.add(new filterdata("NIT Durgapur, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","51680"));
        list.add(new filterdata("NIT Durgapur, Civil (4 Years, B.Tech)","37450"));
        list.add(new filterdata("NIT Durgapur, Computer Science (4 Years, B.Tech)","8345"));
        list.add(new filterdata("NIT Durgapur, Electrical (4 Years, B.Tech)","19785"));
        list.add(new filterdata("NIT Durgapur, Electronics and Communication (4 Years, B.Tech)","13265"));
        list.add(new filterdata("NIT Durgapur, Mechanical (4 Years, B.Tech)","27912"));
        list.add(new filterdata("NIT Durgapur, Metallurgy (4 Years, B.Tech)","43858"));
        list.add(new filterdata("NIT Goa, Civil (4 Years, B.Tech)","41098"));
        list.add(new filterdata("NIT Goa, Computer Science (4 Years, B.Tech)","11419"));
        list.add(new filterdata("NIT Goa, Electrical and Electronics (4 Years, B.Tech)","22186"));
        list.add(new filterdata("NIT Goa, Electronics and Communication (4 Years, B.Tech)","17449"));
        list.add(new filterdata("NIT Goa, Mechanical (4 Years, B.Tech)","28232"));
        list.add(new filterdata("NIT Hamirpur, Architecture (5 Years, Bachelor of Architecture)","1236"));
        list.add(new filterdata("NIT Hamirpur, Chemical (4 Years, B.Tech)","37944"));
        list.add(new filterdata("NIT Hamirpur, Civil (4 Years, B.Tech)","40957"));
        list.add(new filterdata("NIT Hamirpur, Computer Science (4 Years, B.Tech)","10499"));
        list.add(new filterdata("NIT Hamirpur, Computer Science (5 Years, B.Tech/M.Tech (Dual Degree))","12365"));
        list.add(new filterdata("NIT Hamirpur, Electrical (4 Years, B.Tech)","24427"));
        list.add(new filterdata("NIT Hamirpur, Electronics and Communication (4 Years, B.Tech)","16580"));
        list.add(new filterdata("NIT Hamirpur, Electronics and Communication (5 Years, B.Tech/M.Tech (Dual Degree))","21328"));
        list.add(new filterdata("NIT Hamirpur, Engineering Physics (4 Years, B.Tech)","27758"));
        list.add(new filterdata("NIT Hamirpur, Materials Science and Engineering (4 Years, B.Tech)","45343"));
        list.add(new filterdata("NIT Hamirpur, Mathematics and Computing (4 Years, B.Tech)","14267"));
        list.add(new filterdata("NIT Hamirpur, Mechanical (4 Years, B.Tech)","31202"));
        list.add(new filterdata("NIT Surathkal, Artificial Intelligence (4 Years, B.Tech)","1798"));
        list.add(new filterdata("NIT Surathkal, Chemical (4 Years, B.Tech)","14553"));
        list.add(new filterdata("NIT Surathkal, Civil (4 Years, B.Tech)","19187"));
        list.add(new filterdata("NIT Surathkal, Computer Science (4 Years, B.Tech)","1111"));
        list.add(new filterdata("NIT Surathkal, Electrical and Electronics (4 Years, B.Tech)","7315"));
        list.add(new filterdata("NIT Surathkal, Electronics and Communication (4 Years, B.Tech)","4246"));
        list.add(new filterdata("NIT Surathkal, Information Technology (4 Years, B.Tech)","1933"));
        list.add(new filterdata("NIT Surathkal, Mechanical (4 Years, B.Tech)","11458"));
        list.add(new filterdata("NIT Surathkal, Metallurgy (4 Years, B.Tech)","22386"));
        list.add(new filterdata("NIT Surathkal, Mining (4 Years, B.Tech)","26177"));
        list.add(new filterdata("NIT Meghalaya, Civil (4 Years, B.Tech)","52661"));
        list.add(new filterdata("NIT Meghalaya, Computer Science (4 Years, B.Tech)","19728"));
        list.add(new filterdata("NIT Meghalaya, Electrical and Electronics (4 Years, B.Tech)","37540"));
        list.add(new filterdata("NIT Meghalaya, Electronics and Communication (4 Years, B.Tech)","28092"));
        list.add(new filterdata("NIT Meghalaya, Mechanical (4 Years, B.Tech)","46562"));
        list.add(new filterdata("NIT Nagaland, Civil (4 Years, B.Tech)","57952"));
        list.add(new filterdata("NIT Nagaland, Computer Science (4 Years, B.Tech)","31230"));
        list.add(new filterdata("NIT Nagaland, Electrical and Electronics (4 Years, B.Tech)","47870"));
        list.add(new filterdata("NIT Nagaland, Electronics and Communication (4 Years, B.Tech)","41534"));
        list.add(new filterdata("NIT Nagaland, Electronics and Instrumentation (4 Years, B.Tech)","50010"));
        list.add(new filterdata("NIT Nagaland, Mechanical (4 Years, B.Tech)","54423"));
        list.add(new filterdata("NIT Patna, Architecture (5 Years, Bachelor of Architecture)","1324"));
        list.add(new filterdata("NIT Patna, Civil (4 Years, B.Tech)","43265"));
        list.add(new filterdata("NIT Patna, Computer Science (4 Years, B.Tech)","14394"));
        list.add(new filterdata("NIT Patna, Electrical (4 Years, B.Tech)","28195"));
        list.add(new filterdata("NIT Patna, Electronics and Communication (4 Years, B.Tech)","21973"));
        list.add(new filterdata("NIT Patna, Mechanical (4 Years, B.Tech)","37059"));
        list.add(new filterdata("NIT Puducherry, Civil (4 Years, B.Tech)","43557"));
        list.add(new filterdata("NIT Puducherry, Computer Science (4 Years, B.Tech)","16126"));
        list.add(new filterdata("NIT Puducherry, Electrical and Electronics (4 Years, B.Tech)","33117"));
        list.add(new filterdata("NIT Puducherry, Electronics and Communication (4 Years, B.Tech)","23031"));
        list.add(new filterdata("NIT Puducherry, Mechanical (4 Years, B.Tech)","34703"));
        list.add(new filterdata("NIT Raipur, Architecture (5 Years, Bachelor of Architecture)","1237"));
        list.add(new filterdata("NIT Raipur, Bio Medical Engineering (4 Years, B.Tech)","53853"));
        list.add(new filterdata("NIT Raipur, Bio Technology (4 Years, B.Tech)","50203"));
        list.add(new filterdata("NIT Raipur, Chemical (4 Years, B.Tech)","39898"));
        list.add(new filterdata("NIT Raipur, Civil (4 Years, B.Tech)","41146"));
        list.add(new filterdata("NIT Raipur, Computer Science (4 Years, B.Tech)","10903"));
        list.add(new filterdata("NIT Raipur, Electrical (4 Years, B.Tech)","25527"));
        list.add(new filterdata("NIT Raipur, Electronics and Communication (4 Years, B.Tech)","17379"));
        list.add(new filterdata("NIT Raipur, Information Technology (4 Years, B.Tech)","13636"));
        list.add(new filterdata("NIT Raipur, Mechanical (4 Years, B.Tech)","31771"));
        list.add(new filterdata("NIT Raipur, Metallurgy (4 Years, B.Tech)","49234"));
        list.add(new filterdata("NIT Raipur, Mining (4 Years, B.Tech)","51335"));
        list.add(new filterdata("NIT Sikkim, Civil (4 Years, B.Tech)","54730"));
        list.add(new filterdata("NIT Sikkim, Computer Science (4 Years, B.Tech)","26475"));
        list.add(new filterdata("NIT Sikkim, Electrical and Electronics (4 Years, B.Tech)","41478"));
        list.add(new filterdata("NIT Sikkim, Electronics and Communication (4 Years, B.Tech)","37950"));
        list.add(new filterdata("NIT Sikkim, Mechanical (4 Years, B.Tech)","47504"));
        list.add(new filterdata("NIT Arunachal Pradesh, Civil (4 Years, B.Tech)","56427"));
        list.add(new filterdata("NIT Arunachal Pradesh, Computer Science (4 Years, B.Tech)","28763"));
        list.add(new filterdata("NIT Arunachal Pradesh, Electrical (4 Years, B.Tech)","46792"));
        list.add(new filterdata("NIT Arunachal Pradesh, Electronics and Communication (4 Years, B.Tech)","39301"));
        list.add(new filterdata("NIT Arunachal Pradesh, Mechanical (4 Years, B.Tech)","52337"));
        list.add(new filterdata("NIT Jamshedpur, Civil (4 Years, B.Tech)","35817"));
        list.add(new filterdata("NIT Jamshedpur, Computer Science (4 Years, B.Tech)","8569"));
        list.add(new filterdata("NIT Jamshedpur, Electrical (4 Years, B.Tech)","21333"));
        list.add(new filterdata("NIT Jamshedpur, Electronics and Communication (4 Years, B.Tech)","14247"));
        list.add(new filterdata("NIT Jamshedpur, Mechanical (4 Years, B.Tech)","27602"));
        list.add(new filterdata("NIT Jamshedpur, Metallurgy (4 Years, B.Tech)","43220"));
        list.add(new filterdata("NIT Jamshedpur, Production (4 Years, B.Tech)","41514"));
        list.add(new filterdata("NIT Kurukshetra, Civil (4 Years, B.Tech)","35376"));
        list.add(new filterdata("NIT Kurukshetra, Computer Engineering (4 Years, B.Tech)","5976"));
        list.add(new filterdata("NIT Kurukshetra, Electrical (4 Years, B.Tech)","17768"));
        list.add(new filterdata("NIT Kurukshetra, Electronics and Communication (4 Years, B.Tech)","12006"));
        list.add(new filterdata("NIT Kurukshetra, Information Technology (4 Years, B.Tech)","7504"));
        list.add(new filterdata("NIT Kurukshetra, Mechanical (4 Years, B.Tech)","24586"));
        list.add(new filterdata("NIT Kurukshetra, Production (4 Years, B.Tech)","39475"));
        list.add(new filterdata("NIT Manipur, Civil (4 Years, B.Tech)","54187"));
        list.add(new filterdata("NIT Manipur, Computer Science (4 Years, B.Tech)","29628"));
        list.add(new filterdata("NIT Manipur, Electrical (4 Years, B.Tech)","46926"));
        list.add(new filterdata("NIT Manipur, Electronics and Communication (4 Years, B.Tech)","37302"));
        list.add(new filterdata("NIT Manipur, Mechanical (4 Years, B.Tech)","52426"));
        list.add(new filterdata("NIT Mizoram, Civil (4 Years, B.Tech)","58055"));
        list.add(new filterdata("NIT Mizoram, Computer Science (4 Years, B.Tech)","31967"));
        list.add(new filterdata("NIT Mizoram, Electrical and Electronics (4 Years, B.Tech)","47164"));
        list.add(new filterdata("NIT Mizoram, Electronics and Communication (4 Years, B.Tech)","43465"));
        list.add(new filterdata("NIT Mizoram, Mechanical (4 Years, B.Tech)","54715"));
        list.add(new filterdata("NIT Rourkela, Architecture (5 Years, Bachelor of Architecture)","715"));
        list.add(new filterdata("NIT Rourkela, Bio Medical Engineering (4 Years, B.Tech)","37342"));
        list.add(new filterdata("NIT Rourkela, Bio Technology (4 Years, B.Tech)","32107"));
        list.add(new filterdata("NIT Rourkela, Ceramic Engineering (4 Years, B.Tech)","40150"));
        list.add(new filterdata("NIT Rourkela, Ceramic Engineering (5 Years, B.Tech/M.Tech (Dual Degree))","42668"));
        list.add(new filterdata("NIT Rourkela, Chemical (4 Years, B.Tech)","20239"));
        list.add(new filterdata("NIT Rourkela, Chemical (5 Years, B.Tech/M.Tech (Dual Degree))","26952"));
        list.add(new filterdata("NIT Rourkela, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","45996"));
        list.add(new filterdata("NIT Rourkela, Civil (4 Years, B.Tech)","24581"));
        list.add(new filterdata("NIT Rourkela, Computer Science (4 Years, B.Tech)","2327"));
        list.add(new filterdata("NIT Rourkela, Electrical (4 Years, B.Tech)","11544"));
        list.add(new filterdata("NIT Rourkela, Electronics and Communication (4 Years, B.Tech)","6164"));
        list.add(new filterdata("NIT Rourkela, Electronics and Instrumentation (4 Years, B.Tech)","13438"));
        list.add(new filterdata("NIT Rourkela, Food Process Engineering (4 Years, B.Tech)","40190"));
        list.add(new filterdata("NIT Rourkela, Industrial Design (4 Years, B.Tech)","32588"));
        list.add(new filterdata("NIT Rourkela, Life Science (5 Years, B.Sc/M.Sc (Dual Degree))","57878"));
        list.add(new filterdata("NIT Rourkela, Mathematics (5 Years, B.Sc/M.Sc (Dual Degree))","20703"));
        list.add(new filterdata("NIT Rourkela, Mechanical (4 Years, B.Tech)","16075"));
        list.add(new filterdata("NIT Rourkela, Metallurgy (4 Years, B.Tech)","28398"));
        list.add(new filterdata("NIT Rourkela, Metallurgy (5 Years, B.Tech/M.Tech (Dual Degree))","30886"));
        list.add(new filterdata("NIT Rourkela, Mining (4 Years, B.Tech)","35060"));
        list.add(new filterdata("NIT Rourkela, Mining (5 Years, B.Tech/M.Tech (Dual Degree))","35458"));
        list.add(new filterdata("NIT Rourkela, Physics (5 Years, B.Sc/M.Sc (Dual Degree))","21374"));
        list.add(new filterdata("NIT Silchar, Civil (4 Years, B.Tech)","44894"));
        list.add(new filterdata("NIT Silchar, Computer Science (4 Years, B.Tech)","12477"));
        list.add(new filterdata("NIT Silchar, Electrical (4 Years, B.Tech)","29545"));
        list.add(new filterdata("NIT Silchar, Electronics and Communication (4 Years, B.Tech)","18809"));
        list.add(new filterdata("NIT Silchar, Electronics and Instrumentation (4 Years, B.Tech)","27839"));
        list.add(new filterdata("NIT Silchar, Mechanical (4 Years, B.Tech)","33750"));
        list.add(new filterdata("NIT Srinagar, Chemical (4 Years, B.Tech)","56504"));
        list.add(new filterdata("NIT Srinagar, Civil (4 Years, B.Tech)","57541"));
        list.add(new filterdata("NIT Srinagar, Computer Science (4 Years, B.Tech)","26253"));
        list.add(new filterdata("NIT Srinagar, Electrical (4 Years, B.Tech)","45495"));
        list.add(new filterdata("NIT Srinagar, Electronics and Communication (4 Years, B.Tech)","36676"));
        list.add(new filterdata("NIT Srinagar, Information Technology (4 Years, B.Tech)","30958"));
        list.add(new filterdata("NIT Srinagar, Mechanical (4 Years, B.Tech)","52104"));
        list.add(new filterdata("NIT Srinagar, Metallurgy (4 Years, B.Tech)","58665"));
        list.add(new filterdata("NIT Tiruchirappalli, Architecture (5 Years, Bachelor of Architecture)","586"));
        list.add(new filterdata("NIT Tiruchirappalli, Chemical (4 Years, B.Tech)","14501"));
        list.add(new filterdata("NIT Tiruchirappalli, Civil (4 Years, B.Tech)","16034"));
        list.add(new filterdata("NIT Tiruchirappalli, Computer Science (4 Years, B.Tech)","714"));
        list.add(new filterdata("NIT Tiruchirappalli, Electrical and Electronics (4 Years, B.Tech)","6417"));
        list.add(new filterdata("NIT Tiruchirappalli, Electronics and Communication (4 Years, B.Tech)","3105"));
        list.add(new filterdata("NIT Tiruchirappalli, Instrumentation and Control (4 Years, B.Tech)","14372"));
        list.add(new filterdata("NIT Tiruchirappalli, Mechanical (4 Years, B.Tech)","9035"));
        list.add(new filterdata("NIT Tiruchirappalli, Metallurgy (4 Years, B.Tech)","20612"));
        list.add(new filterdata("NIT Tiruchirappalli, Production Engineering (4 Years, B.Tech)","22732"));
        list.add(new filterdata("NIT Uttarakhand, Civil (4 Years, B.Tech)","45890"));
        list.add(new filterdata("NIT Uttarakhand, Computer Science (4 Years, B.Tech)","16613"));
        list.add(new filterdata("NIT Uttarakhand, Electrical and Electronics (4 Years, B.Tech)","31353"));
        list.add(new filterdata("NIT Uttarakhand, Electronics and Communication (4 Years, B.Tech)","23391"));
        list.add(new filterdata("NIT Uttarakhand, Mechanical (4 Years, B.Tech)","39706"));
        list.add(new filterdata("NIT Warangal, Bio Technology (4 Years, B.Tech)","25973"));
        list.add(new filterdata("NIT Warangal, Chemical (4 Years, B.Tech)","17305"));
        list.add(new filterdata("NIT Warangal, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","36719"));
        list.add(new filterdata("NIT Warangal, Civil (4 Years, B.Tech)","20883"));
        list.add(new filterdata("NIT Warangal, Computer Science (4 Years, B.Tech)","1520"));
        list.add(new filterdata("NIT Warangal, Electrical and Electronics (4 Years, B.Tech)","8282"));
        list.add(new filterdata("NIT Warangal, Electronics and Communication (4 Years, B.Tech)","5092"));
        list.add(new filterdata("NIT Warangal, Mathematics (5 Years, B.Sc/M.Sc (Dual Degree))","19962"));
        list.add(new filterdata("NIT Warangal, Mechanical (4 Years, B.Tech)","12485"));
        list.add(new filterdata("NIT Warangal, Metallurgy (4 Years, B.Tech)","24237"));
        list.add(new filterdata("NIT Warangal, Physics (5 Years, B.Sc/M.Sc (Dual Degree))","23270"));
        list.add(new filterdata("NIT Surat, Chemical (4 Years, B.Tech)","31460"));
        list.add(new filterdata("NIT Surat, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","56103"));
        list.add(new filterdata("NIT Surat, Civil (4 Years, B.Tech)","34995"));
        list.add(new filterdata("NIT Surat, Computer Science (4 Years, B.Tech)","5337"));
        list.add(new filterdata("NIT Surat, Electrical (4 Years, B.Tech)","17785"));
        list.add(new filterdata("NIT Surat, Electronics and Communication (4 Years, B.Tech)","12810"));
        list.add(new filterdata("NIT Surat, Mathematics (5 Years, B.Sc/M.Sc (Dual Degree))","48278"));
        list.add(new filterdata("NIT Surat, Mechanical (4 Years, B.Tech)","24985"));
        list.add(new filterdata("NIT Surat, Physics (5 Years, B.Sc/M.Sc (Dual Degree))","48402"));
        list.add(new filterdata("NIT Nagpur, Architecture (5 Years, Bachelor of Architecture)","1088"));
        list.add(new filterdata("NIT Nagpur, Chemical (4 Years, B.Tech)","30181"));
        list.add(new filterdata("NIT Nagpur, Civil (4 Years, B.Tech)","32659"));
        list.add(new filterdata("NIT Nagpur, Computer Science (4 Years, B.Tech)","4751"));
        list.add(new filterdata("NIT Nagpur, Electrical and Electronics (4 Years, B.Tech)","15135"));
        list.add(new filterdata("NIT Nagpur, Electronics and Communication (4 Years, B.Tech)","10276"));
        list.add(new filterdata("NIT Nagpur, Mechanical (4 Years, B.Tech)","21346"));
        list.add(new filterdata("NIT Nagpur, Metallurgy (4 Years, B.Tech)","40366"));
        list.add(new filterdata("NIT Nagpur, Mining (4 Years, B.Tech)","42403"));
        list.add(new filterdata("NIT Andhra Pradesh, Bio Technology (4 Years, B.Tech)","54123"));
        list.add(new filterdata("NIT Andhra Pradesh, Chemical (4 Years, B.Tech)","43302"));
        list.add(new filterdata("NIT Andhra Pradesh, Civil (4 Years, B.Tech)","49142"));
        list.add(new filterdata("NIT Andhra Pradesh, Computer Science (4 Years, B.Tech)","18795"));
        list.add(new filterdata("NIT Andhra Pradesh, Electrical and Electronics (4 Years, B.Tech)","36331"));
        list.add(new filterdata("NIT Andhra Pradesh, Electronics and Communication (4 Years, B.Tech)","28570"));
        list.add(new filterdata("NIT Andhra Pradesh, Mechanical (4 Years, B.Tech)","41928"));
        list.add(new filterdata("NIT Andhra Pradesh, Metallurgy (4 Years, B.Tech)","52920"));
        list.add(new filterdata("IIEST Shibpur, Aerospace (4 Years, B.Tech)","22430"));
        list.add(new filterdata("IIEST Shibpur, Architecture (5 Years, Bachelor of Architecture)","836"));
        list.add(new filterdata("IIEST Shibpur, Civil (4 Years, B.Tech)","43906"));
        list.add(new filterdata("IIEST Shibpur, Computer Science (4 Years, B.Tech)","11961"));
        list.add(new filterdata("IIEST Shibpur, Electrical (4 Years, B.Tech)","24897"));
        list.add(new filterdata("IIEST Shibpur, Electronics and Telecommunication (4 Years, B.Tech)","20533"));
        list.add(new filterdata("IIEST Shibpur, Information Technology (4 Years, B.Tech)","15331"));
        list.add(new filterdata("IIEST Shibpur, Mechanical (4 Years, B.Tech)","31973"));
        list.add(new filterdata("IIEST Shibpur, Metallurgy (4 Years, B.Tech)","46385"));
        list.add(new filterdata("IIEST Shibpur, Mining (4 Years, B.Tech)","50543"));
        list.add(new filterdata("IIIT Gwalior, Computer Science (4 Years, B.Tech)","8292"));
        list.add(new filterdata("IIIT Gwalior, B. Tech.(IT) and M. Tech (IT) (5 Years, Integrated B. Tech. and M. Tech.)","12740"));
        list.add(new filterdata("IIIT Gwalior, B. Tech.(IT) and MBA (5 Years, Integrated B. Tech. and MBA)","16786"));
        list.add(new filterdata("IIIT Kota, Computer Science (4 Years, B.Tech)","22460"));
        list.add(new filterdata("IIIT Kota, Electronics and Communication (4 Years, B.Tech)","28322"));
        list.add(new filterdata("IIIT Guwahati, Computer Science (4 Years, B.Tech)","22029"));
        list.add(new filterdata("IIIT Guwahati, Electronics and Communication (4 Years, B.Tech)","32159"));
        list.add(new filterdata("IIIT Kalyani, Computer Science (4 Years, B.Tech)","36909"));
        list.add(new filterdata("IIIT Kalyani, Electronics and Communication (4 Years, B.Tech)","43855"));
        list.add(new filterdata("IIIT Sonepat, Computer Science (4 Years, B.Tech)","18513"));
        list.add(new filterdata("IIIT Sonepat, Information Technology (4 Years, B.Tech)","20874"));
        list.add(new filterdata("IIIT Una, Computer Science (4 Years, B.Tech)","32165"));
        list.add(new filterdata("IIIT Una, Electronics and Communication (4 Years, B.Tech)","43601"));
        list.add(new filterdata("IIIT Una, Information Technology (4 Years, B.Tech)","36202"));
        list.add(new filterdata("IIIT  Sri City, Computer Science (4 Years, B.Tech)","26606"));
        list.add(new filterdata("IIIT  Sri City, Electronics and Communication (4 Years, B.Tech)","36154"));
        list.add(new filterdata("IIIT Vadodara, Computer Science (4 Years, B.Tech)","19199"));
        list.add(new filterdata("IIIT Vadodara, Information Technology (4 Years, B.Tech)","22042"));
        list.add(new filterdata("IIIT Allahabad, Electronics and Communication (4 Years, B.Tech)","9358"));
        list.add(new filterdata("IIIT Allahabad, Information Technology (4 Years, B.Tech)","4876"));
        list.add(new filterdata("IIIT Allahabad, Information Technology-Business Informatics (4 Years, B.Tech)","5300"));
        list.add(new filterdata("IIIT Kancheepuram, Computer Science (4 Years, B.Tech)","16624"));
        list.add(new filterdata("IIIT Kancheepuram, Computer Science with Major in Artificial Intelligence (4 Years, B.Tech)","16721"));
        list.add(new filterdata("IIIT Kancheepuram, Electronics and Communication (4 Years, B.Tech)","28108"));
        list.add(new filterdata("IIIT Kancheepuram, Mechanical (4 Years, B.Tech)","44819"));
        list.add(new filterdata("IIIT Kancheepuram, Smart Manufacturing (4 Years, B.Tech)","50737"));
        list.add(new filterdata("IIIT Jabalpur, Computer Science (4 Years, B.Tech)","15755"));
        list.add(new filterdata("IIIT Jabalpur, Electronics and Communication (4 Years, B.Tech)","24411"));
        list.add(new filterdata("IIIT Jabalpur, Mechanical (4 Years, B.Tech)","39162"));
        list.add(new filterdata("IIIT Jabalpur, Smart Manufacturing (4 Years, B.Tech)","50826"));
        list.add(new filterdata("IIIT Manipur, Computer Science (4 Years, B.Tech)","45842"));
        list.add(new filterdata("IIIT Manipur, Electronics and Communication (4 Years, B.Tech)","49171"));
        list.add(new filterdata("IIIT Tiruchirappalli, Computer Science (4 Years, B.Tech)","22826"));
        list.add(new filterdata("IIIT Tiruchirappalli, Electronics and Communication (4 Years, B.Tech)","34131"));
        list.add(new filterdata("IIIT Lucknow, Computer Science (4 Years, B.Tech)","9237"));
        list.add(new filterdata("IIIT Lucknow, Computer Science and Artificial Intelligence (4 Years, B.Tech)","8869"));
        list.add(new filterdata("IIIT Lucknow, Computer Science and Business (4 Years, B.Tech)","10753"));
        list.add(new filterdata("IIIT Lucknow, Information Technology (4 Years, B.Tech)","11031"));
        list.add(new filterdata("IIIT Dharwad, Computer Science (4 Years, B.Tech)","31578"));
        list.add(new filterdata("IIIT Dharwad, Data Science and Artificial Intelligence (4 Years, B.Tech)","33907"));
        list.add(new filterdata("IIIT Dharwad, Electronics and Communication (4 Years, B.Tech)","39254"));
        list.add(new filterdata("IIIT Kurnool, Artificial Intelligence and Data Science (4 Years, B.Tech)","35867"));
        list.add(new filterdata("IIIT Kurnool, Computer Science (4 Years, B.Tech)","31549"));
        list.add(new filterdata("IIIT Kurnool, Electronics and Communication with specialization in Design and Manufacturing (4 Years, B.Tech)","42914"));
        list.add(new filterdata("IIIT Kurnool, Mechanical with specialization in Design and Manufacturing (4 Years, B.Tech)","51769"));
        list.add(new filterdata("IIIT Kottayam, Computer Science (4 Years, B.Tech)","34158"));
        list.add(new filterdata("IIIT Kottayam, Computer Science with specialization in Cyber Security (4 Years, B.Tech)","35997"));
        list.add(new filterdata("IIIT Kottayam, Electronics and Communication (4 Years, B.Tech)","40001"));
        list.add(new filterdata("IIIT  Ranchi, Computer Science (4 Years, B.Tech)","35619"));
        list.add(new filterdata("IIIT  Ranchi, Computer Science (with Specialization of Data Science and Artificial Intelligence) (4 Years, B. Tech / B. Tech (Hons.))","33806"));
        list.add(new filterdata("IIIT  Ranchi, Electronics and Communication (4 Years, B.Tech)","44034"));
        list.add(new filterdata("IIIT  Ranchi, Electronics and Communication (with Specialization of Embedded Systems and Internet of Things) (4 Years, B. Tech / B. Tech (Hons.))","42245"));
        list.add(new filterdata("IIIT Nagpur, Computer Science (4 Years, B.Tech)","26772"));
        list.add(new filterdata("IIIT Nagpur, Electronics and Communication (4 Years, B.Tech)","35990"));
        list.add(new filterdata("IIIT Pune, Computer Science (4 Years, B.Tech)","17042"));
        list.add(new filterdata("IIIT Pune, Electronics and Communication (4 Years, B.Tech)","22289"));
        list.add(new filterdata("IIIT Bhagalpur, Computer Science (4 Years, B.Tech)","39316"));
        list.add(new filterdata("IIIT Bhagalpur, Electronics and Communication (4 Years, B.Tech)","45342"));
        list.add(new filterdata("IIIT Bhagalpur, Mechatronics Engineering (4 Years, B.Tech)","50551"));
        list.add(new filterdata("IIIT Bhopal, Computer Science (4 Years, B.Tech)","27852"));
        list.add(new filterdata("IIIT Bhopal, Electronics and Communication (4 Years, B.Tech)","38370"));
        list.add(new filterdata("IIIT Bhopal, Information Technology (4 Years, B.Tech)","32087"));
        list.add(new filterdata("IIIT Surat, Computer Science (4 Years, B.Tech)","21541"));
        list.add(new filterdata("IIIT Surat, Electronics and Communication (4 Years, B.Tech)","27087"));
        list.add(new filterdata("IIIT Agartala, Computer Science (4 Years, B.Tech)","40963"));
        list.add(new filterdata("IIIT Raichur, Computer Science (4 Years, B.Tech)","26361"));
        list.add(new filterdata("IIIT Vadodara, (IIITVICD), Computer Science (4 Years, B.Tech)","32346"));
        list.add(new filterdata("Assam University, Silchar, Agricultural Engineering (4 Years, B.Tech)","78744"));
        list.add(new filterdata("Assam University, Silchar, Computer Science (4 Years, B.Tech)","49422"));
        list.add(new filterdata("Assam University, Silchar, Electronics and Communication (4 Years, B.Tech)","56944"));
        list.add(new filterdata("BIT Mesra, Architecture (5 Years, Bachelor of Architecture)","1298"));
        list.add(new filterdata("BIT Mesra, Bio Technology (4 Years, B.Tech)","57088"));
        list.add(new filterdata("BIT Mesra, Chemical (4 Years, B.Tech)","50527"));
        list.add(new filterdata("BIT Mesra, Chemistry (5 Years, B.Sc/M.Sc (Dual Degree))","61455"));
        list.add(new filterdata("BIT Mesra, Civil (4 Years, B.Tech)","48635"));
        list.add(new filterdata("BIT Mesra, Computer Science (4 Years, B.Tech)","11521"));
        list.add(new filterdata("BIT Mesra, Electrical and Electronics (4 Years, B.Tech)","27730"));
        list.add(new filterdata("BIT Mesra, Electronics and Communication (4 Years, B.Tech)","22555"));
        list.add(new filterdata("BIT Mesra, Food Technology (5 Years, B.Sc/M.Sc (Dual Degree))","64954"));
        list.add(new filterdata("BIT Mesra, Information Technology (4 Years, B.Tech)","13357"));
        list.add(new filterdata("BIT Mesra, Mathematics and Computing (5 Years, B.Sc/M.Sc (Dual Degree))","28934"));
        list.add(new filterdata("BIT Mesra, Mechanical (4 Years, B.Tech)","39123"));
        list.add(new filterdata("BIT Mesra, Physics (5 Years, B.Sc/M.Sc (Dual Degree))","52888"));
        list.add(new filterdata("BIT Mesra, Production (4 Years, B.Tech)","54201"));
        list.add(new filterdata("BIT Mesra, Quantitative Economics & Data Science (5 Years, B.Sc/M.Sc (Dual Degree))","33626"));
        list.add(new filterdata("Gurukula Kangri Vishwavidyalaya, Computer Science (4 Years, B.Tech)","59898"));
        list.add(new filterdata("Gurukula Kangri Vishwavidyalaya, Electrical (4 Years, B.Tech)","82659"));
        list.add(new filterdata("Gurukula Kangri Vishwavidyalaya, Electronics and Communication (4 Years, B.Tech)","75227"));
        list.add(new filterdata("Gurukula Kangri Vishwavidyalaya, Mechanical (4 Years, B.Tech)","88105"));
        list.add(new filterdata("IICT Bhadohi, Carpet and Textile Technology (4 Years, B.Tech)","90766"));
        list.add(new filterdata("Institute of Infrastructure, Technology, Research and Management-Ahmedabad, Civil (4 Years, B.Tech)","62388"));
        list.add(new filterdata("Institute of Infrastructure, Technology, Research and Management-Ahmedabad, Electrical (4 Years, B.Tech)","53238"));
        list.add(new filterdata("Institute of Infrastructure, Technology, Research and Management-Ahmedabad, Mechanical (4 Years, B.Tech)","59156"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Chemical (4 Years, B.Tech)","84529"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Civil (4 Years, B.Tech)","85001"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Computer Science (4 Years, B.Tech)","59214"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Electronics and Communication (4 Years, B.Tech)","69902"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Production (4 Years, B.Tech)","90267"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Information Technology (4 Years, B.Tech)","65096"));
        list.add(new filterdata("Guru Ghasidas Vishwavidyalaya, Mechanical (4 Years, B.Tech)","87601"));
        list.add(new filterdata("J.K. Institute of Applied Physics & Technology, University of Allahabad, Computer Science (4 Years, B.Tech)","45245"));
        list.add(new filterdata("J.K. Institute of Applied Physics & Technology, University of Allahabad, Electronics and Communication (4 Years, B.Tech)","56490"));
        list.add(new filterdata("National Institute of Electronics and Information Technology, Aurangabad, Electronics System Engineering (4 Years, B.Tech)","56973"));
        list.add(new filterdata("NIFFT Ranchi, Mechanical (4 Years, B.Tech)","66727"));
        list.add(new filterdata("NIFFT Ranchi, Metallurgy (4 Years, B.Tech)","75344"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Chemical (4 Years, B.Tech)","82391"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Computer Science (4 Years, B.Tech)","52635"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Electrical (4 Years, B.Tech)","67331"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Electronics and Communication (4 Years, B.Tech)","61633"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Food Technology (4 Years, B.Tech)","90654"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Instrumentation and Control (4 Years, B.Tech)","85134"));
        list.add(new filterdata("Sant Longowal Institute of Engineering and Technology, Mechanical (4 Years, B.Tech)","85321"));
        list.add(new filterdata("Mizoram University, Aizawl, Architecture (5 Years, Bachelor of Architecture)","1616"));
        list.add(new filterdata("Mizoram University, Aizawl, Civil (4 Years, B.Tech)","86191"));
        list.add(new filterdata("Mizoram University, Aizawl, Computer Engineering (4 Years, B.Tech)","62282"));
        list.add(new filterdata("Mizoram University, Aizawl, Electrical (4 Years, B.Tech)","77237"));
        list.add(new filterdata("Mizoram University, Aizawl, Electronics and Communication (4 Years, B.Tech)","75761"));
        list.add(new filterdata("Mizoram University, Aizawl, Information Technology (4 Years, B.Tech)","68280"));
        list.add(new filterdata("School of Engineering, Tezpur University, Civil (4 Years, B.Tech)","86310"));
        list.add(new filterdata("School of Engineering, Tezpur University, Computer Science (4 Years, B.Tech)","54753"));
        list.add(new filterdata("School of Engineering, Tezpur University, Electrical (4 Years, B.Tech)","76357"));
        list.add(new filterdata("School of Engineering, Tezpur University, Electronics and Communication (4 Years, B.Tech)","64441"));
        list.add(new filterdata("School of Engineering, Tezpur University, Food Engineering and Technology (4 Years, B.Tech)","91931"));
        list.add(new filterdata("School of Engineering, Tezpur University, Mechanical (4 Years, B.Tech)","86025"));
        list.add(new filterdata("School of Planning & Architecture, Bhopal, Architecture (5 Years, Bachelor of Architecture)","442"));
        list.add(new filterdata("School of Planning & Architecture, Bhopal, Planning (4 Years, Bachelor of Planning)","608"));
        list.add(new filterdata("School of Planning & Architecture, New Delhi, Architecture (5 Years, Bachelor of Architecture)","252"));
        list.add(new filterdata("School of Planning & Architecture, New Delhi, Planning (4 Years, Bachelor of Planning)","213"));
        list.add(new filterdata("School of Planning & Architecture: Vijayawada, Architecture (5 Years, Bachelor of Architecture)","575"));
        list.add(new filterdata("School of Planning & Architecture: Vijayawada, Planning (4 Years, Bachelor of Planning)","747"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Architecture (5 Years, Bachelor of Architecture)","1675"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Civil (4 Years, B.Tech)","88531"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Computer Science (4 Years, B.Tech)","61619"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Electrical (4 Years, B.Tech)","84464"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Electronics and Communication (4 Years, B.Tech)","73015"));
        list.add(new filterdata("Shri Mata Vaishno Devi University, Katra, Mechanical (4 Years, B.Tech)","88818"));
        list.add(new filterdata("HNB Garhwal University, Computer Science (4 Years, B.Tech)","60561"));
        list.add(new filterdata("HNB Garhwal University, Electrical and Instrumentation Engineering (4 Years, B.Tech)","81013"));
        list.add(new filterdata("HNB Garhwal University, Electronics and Communication (4 Years, B.Tech)","72289"));
        list.add(new filterdata("HNB Garhwal University, Information Technology (4 Years, B.Tech)","65339"));
        list.add(new filterdata("HNB Garhwal University, Mechanical (4 Years, B.Tech)","80106"));
        list.add(new filterdata("International Institute of Information Technology, Naya Raipur, Computer Science (4 Years, B.Tech)","18834"));
        list.add(new filterdata("International Institute of Information Technology, Naya Raipur, Data Science and Artificial Intelligence (4 Years, B.Tech)","19935"));
        list.add(new filterdata("International Institute of Information Technology, Naya Raipur, Electronics and Communication (4 Years, B.Tech)","31639"));
        list.add(new filterdata("University of Hyderabad, Computer Science (5 Years, B.Tech/M.Tech (Dual Degree))","18515"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Aerospace (4 Years, B.Tech)","28324"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Civil (4 Years, B.Tech)","49272"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Computer Science (4 Years, B.Tech)","9497"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Electrical (4 Years, B.Tech)","25898"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Electronics and Communication (4 Years, B.Tech)","16173"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Mechanical (4 Years, B.Tech)","35838"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Metallurgy (4 Years, B.Tech)","55946"));
        list.add(new filterdata("Punjab Engineering College, Chandigarh, Production (4 Years, B.Tech)","52495"));
        list.add(new filterdata("Jawaharlal Nehru University, Delhi, Computer Science & Engineering (5 Years, B.Tech. + M.Tech./MS (Dual Degree))","22473"));
        list.add(new filterdata("Jawaharlal Nehru University, Delhi, Electronics & Communication Engineering (5 Years, B.Tech. + M.Tech./MS (Dual Degree))","31490"));
        list.add(new filterdata("International Institute of Information Technology, Bhubaneswar, Computer Engineering (4 Years, B.Tech)","25774"));
        list.add(new filterdata("International Institute of Information Technology, Bhubaneswar, Computer Science (4 Years, B.Tech)","21298"));
        list.add(new filterdata("International Institute of Information Technology, Bhubaneswar, Electrical and Electronics (4 Years, B.Tech)","41139"));
        list.add(new filterdata("International Institute of Information Technology, Bhubaneswar, Electronics and Telecommunication (4 Years, B.Tech)","37792"));
        list.add(new filterdata("International Institute of Information Technology, Bhubaneswar, Information Technology (4 Years, B.Tech)","28240"));
        list.add(new filterdata("Central institute of Technology Kokrajar, Civil (4 Years, B.Tech)","76975"));
        list.add(new filterdata("Central institute of Technology Kokrajar, Computer Science (4 Years, B.Tech)","56486"));
        list.add(new filterdata("Central institute of Technology Kokrajar, Electronics and Communication (4 Years, B.Tech)","59654"));
        list.add(new filterdata("Central institute of Technology Kokrajar, Food Engineering and Technology (4 Years, B.Tech)","88631"));
        list.add(new filterdata("Central institute of Technology Kokrajar, Instrumentation Engineering (4 Years, B.Tech)","63870"));
        list.add(new filterdata("Pondicherry Engineering College, Chemical (4 Years, B.Tech)","57691"));
        list.add(new filterdata("Pondicherry Engineering College, Civil (4 Years, B.Tech)","76052"));
        list.add(new filterdata("Pondicherry Engineering College, Computer Science (4 Years, B.Tech)","42585"));
        list.add(new filterdata("Pondicherry Engineering College, Electrical and Electronics (4 Years, B.Tech)","45963"));
        list.add(new filterdata("Pondicherry Engineering College, Electronics and Communication (4 Years, B.Tech)","52850"));
        list.add(new filterdata("Pondicherry Engineering College, Electronics and Instrumentation (4 Years, B.Tech)","49645"));
        list.add(new filterdata("Pondicherry Engineering College, Information Technology (4 Years, B.Tech)","41195"));
        list.add(new filterdata("Pondicherry Engineering College, Mechanical (4 Years, B.Tech)","65103"));
        list.add(new filterdata("Pondicherry Engineering College, Mechatronics Engineering (4 Years, B.Tech)","57381"));
        list.add(new filterdata("Ghani Khan Choudhary Institute of Engineering and Technology, Malda, Electrical (4 Years, B.Tech)","75625"));
        list.add(new filterdata("Ghani Khan Choudhary Institute of Engineering and Technology, Malda, Food Technology (4 Years, B.Tech)","91267"));
        list.add(new filterdata("Ghani Khan Choudhary Institute of Engineering and Technology, Malda, Mechanical (4 Years, B.Tech)","78832"));
        list.add(new filterdata("Central University of Rajasthan, Bio Medical Engineering (4 Years, B.Tech)","73669"));
        list.add(new filterdata("Central University of Rajasthan, Computer Science (4 Years, B.Tech)","40933"));
        list.add(new filterdata("Central University of Rajasthan, Electronics and Communication (4 Years, B.Tech)","52489"));
        list.add(new filterdata("NIFTEM Sonepat, Food Technology and Management (4 Years, B.Tech)","92984"));
        list.add(new filterdata("IIFPT Thanjavur, Food Technology (4 Years, B.Tech)","88001"));
        list.add(new filterdata("North Eastern Regional Institute of Science and Technology, Nirjuli, Civil (4 Years, B.Tech)","65709"));
        list.add(new filterdata("North Eastern Regional Institute of Science and Technology, Nirjuli, Electrical (4 Years, B.Tech)","55289"));
        list.add(new filterdata("North Eastern Regional Institute of Science and Technology, Nirjuli, Electronics and Communication (4 Years, B.Tech)","50447"));
        list.add(new filterdata("North Eastern Regional Institute of Science and Technology, Nirjuli, Mechanical (4 Years, B.Tech)","65649"));
        list.add(new filterdata("IIHT Varanasi, Handloom and Textile Technology (4 Years, B.Tech)","89271"));
        list.add(new filterdata("CSVTU Bhilai, Computer Science Engineering (Artificial Intelligence) (4 Years, B. Tech / B. Tech (Hons.))","58125"));
        list.add(new filterdata("CSVTU Bhilai, Computer Science Engineering (Data Science) (4 Years, B. Tech / B. Tech (Hons.))","61773"));
        list.add(new filterdata("ICT Mumbai, Odisha Campus, Chemical (5 Years, Integrated Masters in Technology)","52147"));
        list.add(new filterdata("North-Eastern Hill University, Shillong, Biomedical Engineering (4 Years, B.Tech)","88015"));
        list.add(new filterdata("North-Eastern Hill University, Shillong, Electronics and Communication (4 Years, B.Tech)","75858"));
        list.add(new filterdata("North-Eastern Hill University, Shillong, Energy (4 Years, B.Tech)","85198"));
        list.add(new filterdata("North-Eastern Hill University, Shillong, Information Technology (4 Years, B.Tech)","68295"));


        int n = list.size();

        for (int i = 0; i < n; i++) {
            filterdata data = list.get(i);
            //if (Integer.parseInt(data.getText2()) >= req) {
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

    }


    private void createPdf(ArrayList<filterdata> list2) {
        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 7050, 1).create();
                PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

                Canvas canvas = myPage.getCanvas();
                // canvas.drawText("Welcome",50,50,paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(20.0f);
                canvas.drawText("JOSAA 2022", pageInfo.getPageWidth() / 2, 20, paint);

                paint.setTextSize(8.0f);
                canvas.drawText("IIT NIT IIIT CFTI", pageInfo.getPageWidth() / 2, 35, paint);

                paint.setTextSize(12.0f);
                canvas.drawText("College w.r.t. Last Year Closing Rank", pageInfo.getPageWidth() / 2, 50, paint);

                paint.setTextSize(8.0f);
                canvas.drawText("App Link : https://leetcode.com/", pageInfo.getPageWidth() / 2, 65, paint);
                canvas.drawLine(180,66,320,66,paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(9.0f);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                canvas.drawRect(8,90,pageInfo.getPageWidth()-10,7050,paint);

                paint.setStrokeWidth(0);
                paint.setStyle(Paint.Style.FILL);

                int startX = 10, startY = 100, endPos = pageInfo.getPageWidth() - 10;
                for (int i = 0; i < list2.size(); i++) {
                    filterdata data = list2.get(i);
                    canvas.drawText(data.getText(), startX, startY, paint);
                    canvas.drawText(data.getText2(), startX + 450, startY, paint);
                    canvas.drawLine(startX,startY+3,endPos,startY+3,paint);
                    startY += 15;
                }

                canvas.drawLine(430,90,430,7050,paint);

                pdfDocument.finishPage(myPage);

                //File file = new File(Environment.getExternalStorageDirectory(),"/FirstPdf.pdf");
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/FirstPDF1.pdf");
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(filter.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
            }
        });
    }


    private void creatExcel(ArrayList<filterdata> list2){
        buttonexl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");

                for(int i=0;i<list2.size();i++) {
                  filterdata filterdata = list2.get(i);
                    HSSFRow hssfRow = hssfSheet.createRow(i);
                    HSSFCell hssfCell = hssfRow.createCell(0);
                    HSSFCell hssfCell1 = hssfRow.createCell(2);

                    hssfCell.setCellValue(filterdata.getText());
                    hssfCell1.setCellValue(filterdata.getText2());
                }


                try {
                    if (!filePath.exists()){
                        filePath.createNewFile();
                    }

                    FileOutputStream fileOutputStream= new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);
                    Toast.makeText(filter.this, "Excel file generated successfully.", Toast.LENGTH_SHORT).show();

                    if (fileOutputStream!=null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

           });
        }


    }