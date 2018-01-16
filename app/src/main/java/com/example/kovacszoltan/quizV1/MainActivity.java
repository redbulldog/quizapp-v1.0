package com.example.kovacszoltan.quizV1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_kerdes) TextView tv_kerdes;
    @BindView(R.id.btn_valasz1) Button btn_valasz1;
    @BindView(R.id.btn_valasz2) Button btn_valasz2;
    @BindView(R.id.btn_valasz3) Button btn_valasz3;
    @BindView(R.id.btn_valasz4) Button btn_valasz4;

    private com.example.kovacszoltan.quizV1.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Random r = new Random();
    List<Integer> regikerdes = new ArrayList<Integer>();
    List<String> kerdes = new ArrayList<String>();
    List<String> valasz1 = new ArrayList<String>();
    List<String> valasz2 = new ArrayList<String>();
    List<String> valasz3 = new ArrayList<String>();
    List<String> valasz4 = new ArrayList<String>();
    Cursor c;
    String kerdesekszama_sql, kerdesselect_sql;
    int idIndex, kerdesIndex, valasz1index, valasz2index, valasz3index, valasz4index, idindex;
    int gombsorrend, life = 5, elozogombsorrend = 0, kerdesekszama = 0, kerdesek = 0;
    private AlertDialog.Builder alert_vesztett, alert_kilep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDBHelper = new com.example.kovacszoltan.quizV1.DatabaseHelper(this);
        init();
        ujkerdes();
        gombok();
    }
    public void init(){
        alert_vesztett = new AlertDialog.Builder(MainActivity.this);
        alert_kilep = new AlertDialog.Builder(MainActivity.this);
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
        alert_kilep.setTitle("Biztosan kilép?")
                .setMessage("A jelenlegi állás elveszik!")
                .setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setCancelable(true)
                .setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gomain = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(gomain);
                        finish();
                    }
                });
    }
    public void onBackPressed(){
        alert_kilep.show();
    }
    public void gombok(){
        switch (this.gombsorrend){
            case 1:
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz1.setText(valasz1.get(this.kerdesek));
                btn_valasz2.setText(valasz2.get(this.kerdesek));
                btn_valasz3.setText(valasz3.get(this.kerdesek));
                btn_valasz4.setText(valasz4.get(this.kerdesek));
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();

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
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz4.setText(valasz1.get(this.kerdesek));
                btn_valasz3.setText(valasz2.get(this.kerdesek));
                btn_valasz1.setText(valasz3.get(this.kerdesek));
                btn_valasz2.setText(valasz4.get(this.kerdesek));
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();

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
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz2.setText(valasz1.get(this.kerdesek));
                btn_valasz3.setText(valasz2.get(this.kerdesek));
                btn_valasz4.setText(valasz3.get(this.kerdesek));
                btn_valasz1.setText(valasz4.get(this.kerdesek));
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();

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
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz2.setText(valasz1.get(this.kerdesek));
                btn_valasz4.setText(valasz2.get(this.kerdesek));
                btn_valasz1.setText(valasz3.get(this.kerdesek));
                btn_valasz3.setText(valasz4.get(this.kerdesek));
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ujkerdes();
                        helyesvalasz();

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
            alert_vesztett.show();
        }

    }
    public void ujkerdes() {
        categorieshelper();
        this.gombsorrend = r.nextInt(4) + 1;
        regikerdes.add(21);
        mDb = mDBHelper.getWritableDatabase();
        this.c = mDb.rawQuery("SELECT " + this.kerdesselect_sql, null);
        //Toast.makeText(MainActivity.this, Integer.toString(c.getCount()), Toast.LENGTH_SHORT).show();
        databasetolist();
        if (kerdesekszama < c.getCount()) {
            do {
                this.kerdesek = r.nextInt(c.getCount());
                if (regikerdes.contains(kerdesek)) {
                    this.kerdesek = r.nextInt(c.getCount());
                }
            } while (regikerdes.contains(kerdesek));

            this.kerdesek = r.nextInt(c.getCount());
            regikerdes.add(this.kerdesek);

            kerdesekszama++;
            gombok();
        } else if (kerdesekszama == c.getCount())
        {
            tv_kerdes.setText("A kérdések elfogytak!");
        }
        //Toast.makeText(MainActivity.this, Integer.toString(this.kerdesek), Toast.LENGTH_SHORT).show();



        /*if (kerdesekszama < c.getCount()) {
            do {
                this.idindex = r.nextInt(20) + 1;
                if (regikerdes.contains(idindex)) {
                    this.idindex = r.nextInt(20) + 1;
                }
            } while (regikerdes.contains(idindex));
            kerdesekszama++;
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
                //this.c = mDb.rawQuery("SELECT * FROM quiz", null);

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



            gombok();

        } else if (kerdesekszama == 20) {
            tv_kerdes.setText("A kérdések elfogytak!");
        }*/



    }
private void categorieshelper(){
    SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
    this.kerdesekszama_sql = sharedPreferences.getString("kerdesekszama", "");
    this.kerdesselect_sql = sharedPreferences.getString("kerdesselect", "");

}
private void databasetolist(){
    c.moveToFirst();
    for(int i=0; i < c.getCount(); i++)
    {
        this.idIndex = c.getColumnIndex("id");
        this.kerdesIndex = c.getColumnIndex("kerdes");
        this.valasz1index = c.getColumnIndex("valasz1");
        this.valasz2index = c.getColumnIndex("valasz2");
        this.valasz3index = c.getColumnIndex("valasz3");
        this.valasz4index = c.getColumnIndex("valasz4");
        kerdes.add(c.getString(kerdesIndex));
        valasz1.add(c.getString(valasz1index));
        valasz2.add(c.getString(valasz2index));
        valasz3.add(c.getString(valasz3index));
        valasz4.add(c.getString(valasz4index));
        c.moveToNext();
    }
}

}

