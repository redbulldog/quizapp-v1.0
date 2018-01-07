package com.example.kovacszoltan.designanddatabase;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private com.example.kovacszoltan.designanddatabase.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private TextView tv_kerdes, teszt;
    private Button btn_valasz1, btn_valasz2, btn_valasz3, btn_valasz4;
    private Random r = new Random();
    List<Integer> regikerdes = new ArrayList<Integer>();
    Cursor c;
    int idIndex, kerdesIndex, valasz1index, valasz2index, valasz3index, valasz4index, idindex;
    int gombsorrend, life = 5, elozogombsorrend = 0, kerdesekszama = 0;
    private AlertDialog.Builder alert_vesztett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new com.example.kovacszoltan.designanddatabase.DatabaseHelper(this);
        init();
        ujkerdes();
        gombok();
    }
    public void init(){
        tv_kerdes = (TextView) findViewById(R.id.tv_kerdes);
        btn_valasz1 = (Button) findViewById(R.id.btn_valasz1);
        btn_valasz2 = (Button) findViewById(R.id.btn_valasz2);
        btn_valasz3 = (Button) findViewById(R.id.btn_valasz3);
        btn_valasz4 = (Button) findViewById(R.id.btn_valasz4);
        teszt = (TextView) findViewById(R.id.teszt);
    }
    public void gombok(){
        switch (this.gombsorrend){
            case 1:
                tv_kerdes.setText(this.c.getString(kerdesIndex));
                btn_valasz1.setText(this.c.getString(valasz1index));
                btn_valasz2.setText(this.c.getString(valasz2index));
                btn_valasz3.setText(this.c.getString(valasz3index));
                btn_valasz4.setText(this.c.getString(valasz4index));
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();
                        gombok();
                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 2:
                tv_kerdes.setText(this.c.getString(kerdesIndex));
                btn_valasz4.setText(this.c.getString(valasz1index));
                btn_valasz3.setText(this.c.getString(valasz2index));
                btn_valasz1.setText(this.c.getString(valasz3index));
                btn_valasz2.setText(this.c.getString(valasz4index));
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();
                        gombok();
                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 3:
                tv_kerdes.setText(this.c.getString(kerdesIndex));
                btn_valasz2.setText(this.c.getString(valasz1index));
                btn_valasz3.setText(this.c.getString(valasz2index));
                btn_valasz4.setText(this.c.getString(valasz3index));
                btn_valasz1.setText(this.c.getString(valasz4index));
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();
                        gombok();
                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 4:
                tv_kerdes.setText(this.c.getString(kerdesIndex));
                btn_valasz2.setText(this.c.getString(valasz1index));
                btn_valasz4.setText(this.c.getString(valasz2index));
                btn_valasz1.setText(this.c.getString(valasz3index));
                btn_valasz3.setText(this.c.getString(valasz4index));
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();
                        gombok();
                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
        }
    }
    public void helyesvalasz(){
    }
    public void rosszvalasz(){
        if (life > 1){
            life--;
        } else{
            alert_vesztett = new AlertDialog.Builder(MainActivity.this);
            alert_vesztett.setTitle("Vesztettél!")
                    .setMessage("Újra akarod kezdeni a játékot?")
                    .setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setCancelable(false)
                    .create();
            alert_vesztett.show();
        }

    }
    public void ujkerdes(){
                    this.gombsorrend = r.nextInt(4) + 1;
                    regikerdes.add(21);
            //this.idindex = r.nextInt(20) + 1;
            if (kerdesekszama < 20) {
                do {
                    if (regikerdes.contains(idindex)) {
                        this.idindex = r.nextInt(20) + 1;
                    }
                } while (regikerdes.contains(idindex) == true);
                kerdesekszama++;
            } else if(kerdesekszama == 20){
                tv_kerdes.setText("A kérdések elfogytak!");
            }
        teszt.setText(Integer.toString(this.kerdesekszama));

                try {
                    mDBHelper.updateDataBase();
                } catch (IOException mIOException) {
                    throw new Error("UnableToUpdateDatabase");
                }

                try {
                    mDb = mDBHelper.getWritableDatabase();
                } catch (SQLException mSQLException) {
                    throw mSQLException;
                }
                try {
                    this.c = mDb.rawQuery("SELECT * FROM quiz", null);

                    this.idIndex = c.getColumnIndex("id");
                    this.kerdesIndex = c.getColumnIndex("kerdes");
                    this.valasz1index = c.getColumnIndex("valasz1");
                    this.valasz2index = c.getColumnIndex("valasz2");
                    this.valasz3index = c.getColumnIndex("valasz3");
                    this.valasz4index = c.getColumnIndex("valasz4");

                    this.c.move(idindex);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                regikerdes.add(idindex);
            }
    }

